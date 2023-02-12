## todo-android

Permite a los usuarios crear y administrar una lista de elementos.

La aplicación Todo es una práctica de desarrollo para Android que permite a los usuarios crear y administrar una lista de elementos. Compuesta por dos actividades, una para introducir datos y otra para visualizar la lista de elementos, la aplicación ofrece opciones para editar, marcar como favorito y eliminar elementos.

El proceso de creación de un nuevo elemento se inicia desde la primera actividad a través de un contrato (Contract) que inicia una segunda actividad esperando un resultado. La segunda actividad contiene los campos necesarios para crear un nuevo item o editar un elemento existente.

La vista principal de la aplicación es gestionada por un ReciclerView que se actualiza dinámicamente con cada operación realizada. Antes de eliminar un elemento, se muestra un cuadro de diálogo AlertDialog para confirmar la acción del usuario.
