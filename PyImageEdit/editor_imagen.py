from PIL import Image, ImageEnhance, ImageFilter, ImageOps
import numpy as np

class Filtro:
    def aplicar(self, imagen_pil):
        raise NotImplementedError("Debes implementar el método aplicar")

class FiltroBordes(Filtro):
    def aplicar(self, imagen_pil):
        return imagen_pil.filter(ImageFilter.FIND_EDGES)

class FiltroSepia(Filtro):
    def aplicar(self, imagen_pil):
        img = imagen_pil.convert('RGB')
        arr = np.array(img, dtype=np.float32) / 255.0
        sepia_matrix = np.array([
            [0.393, 0.769, 0.189],
            [0.349, 0.686, 0.168],
            [0.272, 0.534, 0.131]
        ])
        sepia = np.dot(arr, sepia_matrix.T)
        sepia = np.clip(sepia, 0, 1) * 255.0
        sepia = sepia.astype(np.uint8)
        return Image.fromarray(sepia, 'RGB')

class FiltroInvertir(Filtro):
    def aplicar(self, imagen_pil):
        return ImageOps.invert(imagen_pil.convert("RGB"))

class FiltroBinarizar(Filtro):
    def aplicar(self, imagen_pil):
        img = imagen_pil.convert("L")
        return img.point(lambda x: 255 if x > 128 else 0, '1')

class FiltroDesenfoque(Filtro):
    def aplicar(self, imagen_pil):
        return imagen_pil.filter(ImageFilter.GaussianBlur(2))

class FiltroRealzar(Filtro):
    def aplicar(self, imagen_pil):
        return imagen_pil.filter(ImageFilter.SHARPEN)

class FiltroSuavizar(Filtro):
    def aplicar(self, imagen_pil):
        return imagen_pil.filter(ImageFilter.SMOOTH_MORE)

class EditorImagen:
    def __init__(self, imagen):
        self.imagen = imagen
        self.filtros = {
            'sepia': FiltroSepia(),
            'invertir': FiltroInvertir(),
            'binarizar': FiltroBinarizar(),
            'desenfoque': FiltroDesenfoque(),
            'bordes': FiltroBordes(),
            'realzar': FiltroRealzar(),
            'suavizar': FiltroSuavizar()
        }

    def aplicar_operacion(self, nombre, *args):
        operaciones = {
            'grises': self._convertir_escala_grises,
            'rotar': self._rotar,
            'redimensionar': self._redimensionar,
            'espejar': self._espejar,
            'brillo': self._ajustar_brillo,
            'filtro': self._aplicar_filtro
        }
        if nombre in operaciones:
            self.imagen.aplicar_operacion(operaciones[nombre], *args)

    def _convertir_escala_grises(self):
        if self.imagen.imagen_pil.mode != 'L':
            self.imagen.imagen_pil = self.imagen.imagen_pil.convert("L").convert("RGBA")

    def _rotar(self, angulo):
        self.imagen.imagen_pil = self.imagen.imagen_pil.rotate(
            angulo, expand=True, resample=Image.BICUBIC).convert("RGBA")

    def _redimensionar(self, factor):
        ancho, alto = self.imagen.imagen_pil.size
        nuevo_ancho = max(1, int(ancho * factor))
        nuevo_alto = max(1, int(alto * factor))
        if (nuevo_ancho, nuevo_alto) != (ancho, alto):
            self.imagen.imagen_pil = self.imagen.imagen_pil.resize(
                (nuevo_ancho, nuevo_alto), resample=Image.LANCZOS).convert("RGBA")

    def redimensionar_personalizado(self, ancho, alto):
        if ancho and alto:
            self.imagen.aplicar_operacion(
                lambda: self._resize_direct(ancho, alto)
            )

    def _resize_direct(self, ancho, alto):
        self.imagen.imagen_pil = self.imagen.imagen_pil.resize(
            (ancho, alto), resample=Image.LANCZOS).convert("RGBA")

    def espejar(self, eje):
        self.imagen.aplicar_operacion(self._espejar, eje)

    def _espejar(self, eje):
        if eje == 'horizontal':
            self.imagen.imagen_pil = self.imagen.imagen_pil.transpose(Image.FLIP_LEFT_RIGHT).convert("RGBA")
        else:
            self.imagen.imagen_pil = self.imagen.imagen_pil.transpose(Image.FLIP_TOP_BOTTOM).convert("RGBA")

    def _ajustar_brillo(self, factor):
        enhancer = ImageEnhance.Brightness(self.imagen.imagen_pil)
        self.imagen.imagen_pil = enhancer.enhance(factor).convert("RGBA")

    def _aplicar_filtro(self, nombre_filtro):
        if nombre_filtro in self.filtros:
            result = self.filtros[nombre_filtro].aplicar(self.imagen.imagen_pil)
            if result is not None:
                self.imagen.imagen_pil = result.convert("RGBA")
