# AAL-project 2015/2016L
# Mateusz Byczkowski
# zadanie 10LS - "hotel"

Program dostarczajacy algorytmy realizujace rozwiazanie problemu jak najtańszego rozlokowania grupy n osób w hotelu.

Opis problemu: Dana jest grupa n osób, wśród których niektó©e osoby się znają. W hotelu występują 3 rodzaje pokoi: z łazienką(bez telewizora) - koszt: 50zł, z telewizorem(bez łazienki) - koszt: 50zł oraz pokój bez telewizora i bez łazienki - koszt: 30zł. Zadanie polega na jak najtańszym rozlokowaniu gości w hotelu o nieograniczonej liczbie pokojów każdego rodzaju aby każda osoba miała dostęp do telewizora i łazienki bezpośrednio u siebie w pokoju lub przez jakiegoś znajomego.

Zaimplementowane algorytmy:

1.Algorytm zachłanny - niska złożoność, mniej optymalny.
2.Autorski algorytm heurystyczny - wyższa złożoność, bardziej optymalny.

Opis algorytmów znajduje się w dokumentacji - dokumentacja.pdf.

Po uruchomieniu mamy 2 możliwości: 

- albo wczytać dane z pliku o podanej nazwie (opcja "1"):

Plik musi mieć następującą strukturę(n - ilosc osob, pozniej id osób - od 0 do n-1):

n
0 [ilosc znajomych] [id_znajomego] [id_znajomego]...
1 [ilosc znajomych] [id_znajomego] [id_znajomego]...
2 [ilosc znajomych] [id_znajomego] [id_znajomego]...
.. [ilosc znajomych] [id_znajomego]...
.. [ilosc znajomych] [id_znajomego]...
n-1 [ilosc znajomych] [id_znajomego]...

- albo wygenerować dane losowe o zadanej liczbie osób i zadanej gęstości znajomości (od 1 do n-1) - opcja "2"
np. dla:
1 - większość osób ma tylko jednego znajomego
n-1 - średnio każdy ma n/2 znajomych
