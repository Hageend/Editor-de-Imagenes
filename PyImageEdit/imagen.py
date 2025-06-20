from PIL import Image
import pygame

class Imagen:
    def __init__(self, ruta_imagen=None, max_historial=20):
        self.max_historial = max_historial
        self.historial = []
        self.rehacer_pila = []
        self.ruta_original = None
        self.version = 0
        if ruta_imagen:
            self.cargar_desde_archivo(ruta_imagen)
        else:
            self.imagen_pil = Image.new('RGBA', (400, 300), (0, 0, 0, 0))
            self.actualizar_pygame()

    def cargar_desde_archivo(self, ruta_imagen):
        self.ruta_original = ruta_imagen
        self.imagen_pil = Image.open(ruta_imagen)
        if self.imagen_pil.mode != 'RGBA':
            self.imagen_pil = self.imagen_pil.convert("RGBA")
        self.historial = []
        self.rehacer_pila = []
        self.actualizar_pygame()

    def pil_a_pygame(self, pil_image):
        mode = pil_image.mode
        size = pil_image.size
        data = pil_image.tobytes()
        return pygame.image.fromstring(data, size, mode)

    def actualizar_pygame(self):
        self.imagen_pygame = self.pil_a_pygame(self.imagen_pil)
        self.version += 1

    def aplicar_operacion(self, operacion, *args):
        if not self.ruta_original:
            return
        if len(self.historial) >= self.max_historial:
            self.historial.pop(0)
        self.historial.append(self.imagen_pil.copy())
        self.rehacer_pila.clear()
        operacion(*args)
        self.actualizar_pygame()

    def deshacer(self):
        if self.historial:
            self.rehacer_pila.append(self.imagen_pil)
            self.imagen_pil = self.historial.pop()
            self.actualizar_pygame()

    def rehacer(self):
        if self.rehacer_pila:
            self.historial.append(self.imagen_pil)
            self.imagen_pil = self.rehacer_pila.pop()
            self.actualizar_pygame()

    def guardar(self, ruta_destino=None):
        if not self.ruta_original:
            return None
        destino = ruta_destino or self.ruta_original
        self.imagen_pil.save(destino)
        return destino

    def esta_vacia(self):
        return self.ruta_original is None
