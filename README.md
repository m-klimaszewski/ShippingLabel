# Uniwersytet Śląski

## Wydział Nauk Ścisłych i Technicznych

### Skaner Listów Przewozowych

> Wykonał: Mateusz Klimaszewski, gr. PGK 2.

<div style="page-break-after: always; visibility: hidden"> 

</div>

## Spis treści:

- Wstęp:
    - <a href="#BP">Metodyka wykonywania zdjęć</a>
- Preprocessing:
    - <a href="#PP">Preprocessing</a>
- Perspective Transform:
    - <a href="#PT">Perspective Transform</a>
>Projekt został wykonany w języku Java oraz przy wykorzystaniu dodatkowych bibliotek:
> * OpenCV - biblioteka do przetwarzania obrazów
> * ZXing - biblioteka do wykrywania i dekodowania kodów kreskowych
> * Tess4J - biblioteka OCR

> <a href="https://github.com/m-klimaszewski/ShippingLabel">Repozytorium GitHub</a>


<div style="page-break-after: always; visibility: hidden"> 
</div>

<div id="BP"></div>

## Metodyka wykonywania zdjęć:

> Proponowany algorytm do prawidłowego działania wymaga, aby list przewozowy był największym elementem zdjęcia oraz
> znajdował się na wyróżnającym go tle. Zdjęcia robione są z różnego kąta, aby sprawdzić skuteczność algorytmu.
### Zdjęcia przed preprocessingiem

![Images before preprocessing ](src/main/resources/beforePreprocessing.png)

<div style="page-break-after: always; visibility: hidden"> 
</div>


<div id="PP"></div>

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



<div id="PT"></div>

## Transofrmacja etykiety z przestrzeni 3d do 2d
> W tym etapie obraz jest poddawany następującym procesom:
> * Wykrycie największej możliwej krawędzi
> * Sortowanie rogów krawędzi wg. środka ciężkości największej krawędzi
>  * ![Images before preprocessing ](src/main/resources/sortingcorners.jpg)
> * Wykrycie rogów oryginalnego zdjęcia i stworzenie mapy perspektywy
> * Transformacja zdjęcia z przestrzeni 3d do 2d
### Zdjęcia po transformacji
![Images before preprocessing ](src/main/resources/wrappedTransform.png)




<div style="page-break-after: always; visibility: hidden">
</div>

