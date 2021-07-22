# Uniwersytet Śląski

## Wydział Nauk Ścisłych i Technicznych

### Wzorce Projektowe, Zadanie 5 - Dekorator.

> Wykonał: Mateusz Klimaszewski, gr. PGK 2.

<div style="page-break-after: always; visibility: hidden"> 
\pagebreak 
</div>

## Spis treści:

- Wstęp:
    - <a href="#BP">Zdjęcia przed preprocessingiem</a>
- Preprocessing:
    - <a href="#APP">Application.java</a>
- Perspective Transform:
    - <a href="#H">Hero.java</a>
>Projekt został wykonany w języku Java oraz przy wykorzystaniu dodatkowych bibliotek:
> * OpenCV - biblioteka do przetwarzania obrazów
> * ZXing - biblioteka do wykrywania i dekodowania kodów kreskowych
> * Tess4J - biblioteka OCR

> <a href="https://github.com/m-klimaszewski/ShippingLabel">Repozytorium GitHub</a>

<div id="BP"></div>

<div style="page-break-after: always; visibility: hidden"> 
\pagebreak 
</div>

## Metodyka wykonywania zdjęć:

> Proponowany algorytm do prawidłowego działania wymaga, aby list przewozowy był największym elementem zdjęcia oraz
> znajdował się na wyróżnającym go tle. Zdjęcia robione są z różnego kąta, aby sprawdzić skuteczność algorytmu.
###Zdjęcia przed preprocessingiem

![Images before preprocessing ](src/main/resources/beforePreprocessing.png)

<div style="page-break-after: always; visibility: hidden"> 
\pagebreak 
</div>


<div id="APP"></div>

## Preprocessing
>Zdjęcia w pierwszej fazie działania algorytmu są poddawane preprocessingu w celu zwiększenia skuteczności wykrycia 
> największej krawędzi. W tym etapie zdjęcie jest poddawane procesom takim jak:
> * Skalowanie obrazu
>   *  Wykrycie proporcji obrazu (4:3, 16:9) oraz jego orientacji (pionowa, pozioma)
>   *  Proces przeskalowania do określonych w alorytmie roździelczości. Tj.:
>     * 4:3 - 1152x864px
>     * 16:9 - 1280x720px
> * Medianblur 
> * Konwersja obrazu do sklai szarości
> * Canny detector
### Zdjęcia po preprocessingu
![Images before preprocessing ](src/main/resources/afterPreprocessing.png)



<div id="H"></div>

## Transofrmacja etykiety z przestrzeni 3d do 2d
> W tym etapie obraz jest poddawany następującym procesom:
> * Wykrycie największej możliwej krawędzi
> * Sortowanie rogów krawędzi wg. środka ciężkości największej krawędzi
>  * ![Images before preprocessing ](src/main/resources/sortingcorners.jpg)
> * Wykrycie rogów oryginalnego zdjęcia i stworzenie mapy perspektywy
> * Transformacja zdjęcia z przestrzeni 3d do 2d
### Zdjęcia po transformacji
![Images before preprocessing ](src/main/resources/wrappedTransform.png)


```
package com.company;

import javax.print.attribute.HashPrintJobAttributeSet;

public abstract class Hero {
    private static int HP = 100;
    private static int demage = 10;
    private static int armor = 10;
    boolean gloves, helemet, boots, sword, trousers = false;

   String getHero(){
       return "Twój Bohater:\n Hp: "+HP
               + "\nAtak: " + demage
               +"\nArmor: " + armor;
   }

    static void add_equipment(Equipment equipment) {

        HP += equipment.HP();
        demage += equipment.Demage();
        armor += equipment.Armor();
        System.out.println("Dodano: " + equipment.getName() );

    }
   static void remove_equipment(Equipment equipment) {
        HP -= equipment.HP();
        demage -= equipment.Demage();
        armor -= equipment.Armor();
       System.out.println("Usunięto: " + equipment.getName() );
    }


}

```

<div style="page-break-after: always; visibility: hidden"> 
\pagebreak 
</div>

