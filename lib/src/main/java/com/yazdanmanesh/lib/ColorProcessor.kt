package com.yazdanmanesh.lib


import com.squareup.kotlinpoet.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter

class ColorProcessor : AbstractProcessor() {

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        for (typeElement in getTypeElementProcess(roundEnv.rootElements, annotations)) {
            val packageName =
                processingEnv.elementUtils.getPackageOf(typeElement).qualifiedName.toString()
            val typeName = typeElement.simpleName.toString()
//            processingEnv.messager.printMessage(
//                Diagnostic.Kind.NOTE,
//                "type: $packageName.$typeName has found\n"
//            )

            val className = ClassName(packageName, typeName)
            val generatedClassName = ClassName(packageName, typeName + "Colorize")
            val classBuilder = TypeSpec.objectBuilder(generatedClassName)
                .addModifiers(KModifier.PUBLIC)

            val bindFun = FunSpec.builder("bind")
                .addModifiers(KModifier.PUBLIC)
                .addAnnotation(JvmStatic::class.java)
                .addParameter("clazz", className)

            for (vElement in ElementFilter.fieldsIn(typeElement.enclosedElements)) {
                val byColor = vElement.getAnnotation(ByColor::class.java) ?: continue
                bindFun.addStatement(
                    "clazz.%N?.setTextColor(%L)",
                    vElement.simpleName,
                    byColor.color
                )
            }
            classBuilder.addFunction(bindFun.build())

            FileSpec.builder(packageName, generatedClassName.simpleName)
                .addComment("Generated by ColorProcessor")
                .addType(classBuilder.build())
                .build()
                .writeTo(processingEnv.filer)


        }

        return true
    }

    private fun getTypeElementProcess(
        rootElements: Set<Element>,
        annotations: Set<TypeElement>
    ): Set<TypeElement> {
        val result = mutableSetOf<TypeElement>()
        rootElements.forEach { element ->
            if (element !is TypeElement)
                return@forEach

            element.enclosedElements.forEach enclosedRoot@{ enclosedElement ->
                for (mirror in enclosedElement.annotationMirrors) {
                    for (annotation in annotations) {
                        if (mirror.annotationType.asElement() != annotation) continue

                        result.add(element)
                        return@enclosedRoot
                    }
                }
            }
        }
        return result
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> =
        mutableSetOf(ByColor::class.java.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

}