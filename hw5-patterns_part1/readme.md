Паттерн Builder

Реализуйте класс Product (id, title, description, cost, weight, width, length, height) и Builder для него

-----

Паттерн Iterator

Есть ящик. В ящике 4 матрёшки разных цветов. Каждая матрёшка состоит из 10 частей. Нужно программно реализовать данную концепцию. Создать возможность не менять клиентский код обеспечить возможность пересчитывать матрёшки различными способами.

1. Сначала сначала самые маленькие (каждого из 4х цветов), затем побольше и тд до самых больших
2. Сначала одну матрёшку от маленькой до большой, затем другую и тд.
   Создайте класс Box с четырмя внутренними листами строк, реализуйте Iterator который последовательно возвращает все строки из листов коробки