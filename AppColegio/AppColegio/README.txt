AppColegio - Laboratorio N 8 (Room + Navegacion + Listas con SwipeToDismiss)

COMO GENERAR EL APK
1. Abrir Android Studio (Koala o superior) > File > Open > carpeta AppColegio.
2. Esperar el "Gradle Sync" (necesita internet la primera vez).
3. Menu: Build > Build Bundle(s) / APK(s) > Build APK(s).
4. Al terminar aparece "locate": app/build/outputs/apk/debug/app-debug.apk
5. Pasar el APK al celular, abrirlo y permitir "instalar apps desconocidas".

O directo al celular: activar Depuracion USB y presionar Run (triangulo verde).

OPCION SIN ANDROID STUDIO (GitHub)
1. Crear cuenta en github.com y un repositorio nuevo.
2. Subir TODO el contenido de esta carpeta (incluida la carpeta oculta .github).
3. Pestana "Actions" > esperar el check verde de "Build APK" (3-5 min).
4. Entrar a esa ejecucion > "Artifacts" > descargar AppColegio-apk (zip con app-debug.apk).
