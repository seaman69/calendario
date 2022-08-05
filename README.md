# calendario
Se insertan, modifican y consultan tratamientos por iduser

Estas 2 peticiones insertan y actualizan los datos respectivamente, ambas con POST, se envia in body con los parámetros especificados en el RestController
https://microtratamientos.herokuapp.com/treatement/addtreatement
https://microtratamientos.herokuapp.com/treatement/updatetreatement

El siguiente método Get es consumido por el grupo de alimentos y devuelve el nombre de las pastillas que debe tomar entre una hora antes y una hora después del parámetro hour
https://microtratamientos.herokuapp.com/treatement/getpillsbyhour?=iduser{}&hour=hh:mm)

El siguiente método get devuelve los tratamientos por usuario y estado (completed y not_completed). el parámetro status es opcional
https://microtratamientos.herokuapp.com/treatement/gettreatement?=iduser{}&status={}

El siguiente post es usado para decir que una pastilla ya fue tomada
https://microtratamientos.herokuapp.com/treatement/takepill?id&hour=hh:mm    
