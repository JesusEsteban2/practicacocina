# Practica Recetario de cocina

## Descripcion de la aplicación

La aplicación descarga los datos de la api (https://dummyjson.com/) la primera vez y lo 
almacena en la BBDD local en SQLite.

Permite visualizar la lista de recetas, pinchar sobre una para ver el detalle, asi como editar 
la receta para realizar cambios y correcciones.

## Puntos a cumplir
La aplicación dispone de 3 Activities (A)
- /activities/MainActivity
- /activities/DetailActivity
- /activities/EditActivity

Utilizar una Tabla SQLite
- Utiliza la tabla "Recipes" en la BBDD "recetas.db" 
- Se puede ver la implementación en el archivo /data/Database

Utilizar Retrofit (Api Rest)
- Se puede ver la implementación en /data/ApiRetrofit
- Se puede ver su uso en /activities/MainActivity

Utilizar un ReciclerView

Utilizar PutExtra

Utilizar Shared Preferences

Utilizar una App Bar
- Se utiliza en /activities/MainActivity para realizar la busqueda de recetas por nombre.

Utilizar Internacionalización
- 2 Archivos de idiomas en 'strings' Ingles+Español.

Utilizar ViewBinding
- Se utiliza en las 3 activities principales.

Utilizar Material Design (TextField)
- recipe_list.xml líneas 17 a 39
- activity_edit.xml

Utilizar un Dialogo de Alerta
- No está implementado