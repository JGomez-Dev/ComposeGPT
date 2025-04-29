# ComposeGPT

## Descripción
ComposeGPT es una aplicación de demostración para Android que integra la API de OpenAI (GPT) con Jetpack Compose. Esta aplicación permite a los usuarios hacer preguntas a un modelo de lenguaje avanzado y recibir respuestas en tiempo real mediante una interfaz de chat moderna y atractiva.

## Características
- 🧠 Integración con el modelo GPT-4o-mini de OpenAI
- 📱 Interfaz moderna de chat estilo messenger con Jetpack Compose
- 💬 Burbujas de chat con diseño Material 3
- 🔄 Gestión de conversaciones y animaciones fluidas
- ✨ Indicador de escritura durante la espera de respuestas
- 📤 Envío de mensajes con botón o tecla "enviar"

## Capturas de pantalla
*Añadir capturas de pantalla aquí*

## Requisitos
- Android Studio Flamingo o superior
- Android SDK 26+
- Kotlin 1.8.0+
- Una clave API válida de OpenAI

## Configuración
1. Clona este repositorio:
```
git clone https://github.com/jgomez-dev/ComposeGPT.git
```

2. Abre el proyecto en Android Studio.

3. Consigue una clave API de [OpenAI](https://platform.openai.com/).

4. Reemplaza la clave API en el archivo `app/src/main/java/com/jgomez/composegpt/api/OpenAIConfig.kt`:
```kotlin
const val API_KEY = "TU_CLAVE_API"
```

5. Compila y ejecuta la aplicación en un emulador o dispositivo.

## ⚠️ Seguridad de la API Key
**IMPORTANTE:** El repositorio incluye un archivo `OpenAIConfig.kt` con una clave API de ejemplo. 
Debes reemplazarla con tu propia clave y **nunca subir tu clave real a GitHub**.

Opciones para manejar tus claves de forma segura:
1. Usar variables de entorno durante la compilación
2. Usar un archivo de propiedades local no incluido en Git
3. Usar servicios como Secrets Manager o Firebase Remote Config

Para mayor seguridad, considera añadir `app/src/main/java/com/jgomez/composegpt/api/OpenAIConfig.kt` a tu `.gitignore`.

## Arquitectura
La aplicación sigue un patrón de arquitectura simple:

- **Vista (UI)**: Implementada con Jetpack Compose con componentes reutilizables
- **Modelo**: Clases de datos para representar los mensajes y la conversación
- **Red**: Usa Retrofit para las llamadas a la API de OpenAI
- **Configuración**: Centralizada en un objeto de configuración

### Estructura de paquetes
- `com.jgomez.composegpt`: Contiene la actividad principal y pantallas
- `com.jgomez.composegpt.api`: Contiene clases relacionadas con la API (modelos, configuración, servicios)
- `com.jgomez.composegpt.model`: Modelos de datos para la interfaz de usuario 
- `com.jgomez.composegpt.ui.components`: Componentes reutilizables para la interfaz de usuario
- `com.jgomez.composegpt.ui.theme`: Contiene la configuración del tema de la aplicación

## Cómo funciona
1. El usuario escribe un mensaje en el campo de texto inferior
2. Al hacer clic en el botón de enviar, el mensaje se añade a la conversación
3. La aplicación envía la consulta a la API de OpenAI mientras muestra un indicador de "escribiendo"
4. La respuesta del modelo de IA se añade a la conversación como una burbuja de chat
5. La vista se desplaza automáticamente al mensaje más reciente

## Personalización
- **Cambiar el modelo**: Puedes modificar la constante `MODEL` en `OpenAIConfig.kt`.
- **Ajustar la longitud de respuesta**: Modifica `MAX_TOKENS` en `OpenAIConfig.kt`.
- **Cambiar el diseño**: Personaliza los componentes de Compose en `ui/components`.
- **Personalizar colores**: Modifica el tema en `ui/theme`.

## Posibles mejoras
- Implementar persistencia de conversaciones con Room
- Añadir soporte para múltiples conversaciones
- Implementar historial de mensajes para conversaciones contextuales
- Añadir temas claro/oscuro y opciones de personalización
- Integrar TextToSpeech para leer las respuestas
- Añadir soporte para mensajes multimedia (imágenes, audio)

## Contribuciones
Las contribuciones son bienvenidas. Para contribuir:
1. Haz un fork del repositorio.
2. Crea una rama para tu función (`git checkout -b feature/amazingfeature`).
3. Realiza tus cambios y haz commit (`git commit -m 'Añadir nueva característica'`).
4. Sube a la rama (`git push origin feature/amazingfeature`).
5. Abre un Pull Request.

## Licencia
Este proyecto está licenciado bajo la Licencia MIT - consulta el archivo [LICENSE](LICENSE) para más detalles.

## Agradecimientos
- [OpenAI](https://openai.com/) por proporcionar la API de GPT.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) por simplificar el desarrollo de UI en Android.
- [Retrofit](https://square.github.io/retrofit/) para facilitar las llamadas a APIs REST.
- [Material Design 3](https://m3.material.io/) por las guías de diseño moderno.

---

Desarrollado con ❤️ por [Javier Gómez](https://github.com/jgomez-dev) 