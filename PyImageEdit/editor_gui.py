import pygame
import tkinter as tk
from tkinter import filedialog
import tkinter.simpledialog as simpledialog
import os
from imagen import Imagen
from editor_imagen import EditorImagen

class EditorGUI:
    def __init__(self):
        pygame.init()
        pygame.display.set_caption("Editor de Imágenes - Usa 'Abrir'")
        self.ancho, self.alto = 1000, 700
        self.pantalla = pygame.display.set_mode((self.ancho, self.alto), pygame.RESIZABLE)
        pygame.event.set_allowed([
            pygame.QUIT, pygame.VIDEORESIZE, 
            pygame.MOUSEBUTTONDOWN, pygame.MOUSEMOTION, 
            pygame.KEYDOWN, pygame.MOUSEWHEEL
        ])
        self.fuente = pygame.font.SysFont('Arial', 16)
        self.fuente_titulo = pygame.font.SysFont('Arial', 24, bold=True)
        self.fuente_grande = pygame.font.SysFont('Arial', 32, bold=True)
        self.COLOR_FONDO = (30, 35, 45)
        self.COLOR_BOTON = (70, 130, 180)
        self.COLOR_BOTON_HOVER = (100, 160, 210)
        self.COLOR_BOTON_DISABLED = (100, 100, 120)
        self.COLOR_TEXTO = (240, 240, 240)
        self.COLOR_ZONA_ARRATRE = (60, 70, 90)
        self.COLOR_ZONA_ARRATRE_HOVER = (80, 90, 110)
        self.imagen = Imagen()
        self.editor = EditorImagen(self.imagen)
        self.botones = []
        self.menu_scroll = 0
        self.menu_scroll_max = 0
        self.drag_active = False
        self.imagen_escalada = None
        self.cache_version = -1
        self.cache_size = (0, 0)
        self.root_tk = tk.Tk()
        self.root_tk.withdraw()
        self.crear_botones()

    def crear_botones(self):
        self.botones.clear()
        margen = max(10, int(self.ancho * 0.02))
        ancho_boton = max(120, int(self.ancho * 0.18))
        alto_boton = max(30, int(self.alto * 0.05))
        titulo_h = 50
        y_pos = titulo_h + margen - self.menu_scroll
        espacio = max(10, int(self.alto * 0.015)) + alto_boton
        grupos = [
            ("Archivo", [
                ("Abrir imagen", self.abrir_imagen),
                ("Guardar", self.guardar_imagen),
            ]),
            ("Edición", [
                ("Escala de Grises", lambda: self.editor.aplicar_operacion('grises')),
                ("Rotar 90°", lambda: self.editor.aplicar_operacion('rotar', 90)),
                ("Rotar -90°", lambda: self.editor.aplicar_operacion('rotar', -90)),
                ("Redimensionar...", self.redimensionar_dialogo),
                ("Espejar H", lambda: self.editor.aplicar_operacion('espejar', 'horizontal')),
                ("Espejar V", lambda: self.editor.aplicar_operacion('espejar', 'vertical')),
                ("Brillo +", lambda: self.editor.aplicar_operacion('brillo', 1.2)),
                ("Brillo -", lambda: self.editor.aplicar_operacion('brillo', 0.8)),
                ("Deshacer", self.imagen.deshacer),
                ("Rehacer", self.imagen.rehacer),
            ]),
            ("Filtros", [
                ("Desenfoque", lambda: self.aplicar_filtro('desenfoque')),
                ("Bordes", lambda: self.aplicar_filtro('bordes')),
                ("Realzar", lambda: self.aplicar_filtro('realzar')),
                ("Sepia", lambda: self.aplicar_filtro('sepia')),
                ("Invertir", lambda: self.aplicar_filtro('invertir')),
                ("Binarizar", lambda: self.aplicar_filtro('binarizar')),
            ])
        ]
        y_pos_inicial = y_pos
        for nombre_grupo, operaciones in grupos:
            self.botones.append({
                'rect': pygame.Rect(margen, y_pos, ancho_boton, alto_boton),
                'texto': f"[{nombre_grupo}]",
                'accion': None,
                'color': (50, 60, 80),
                'enabled': False,
                'es_titulo': True
            })
            y_pos += alto_boton + espacio // 2
            for texto, accion in operaciones:
                boton = {
                    'rect': pygame.Rect(margen, y_pos, ancho_boton, alto_boton),
                    'texto': texto,
                    'accion': accion,
                    'color': self.COLOR_BOTON,
                    'hover': False,
                    'enabled': True,
                    'es_titulo': False
                }
                self.botones.append(boton)
                y_pos += espacio
            y_pos += espacio // 2
        self.menu_scroll_max = max(0, y_pos - y_pos_inicial - (self.alto - titulo_h - margen*2))

    def abrir_imagen(self):
        ruta_imagen = filedialog.askopenfilename(
            title="Seleccionar imagen",
            filetypes=(
                ("Imágenes", "*.png;*.jpg;*.jpeg;*.bmp;*.tiff"),
                ("Todos los archivos", "*.*")
            )
        )
        if ruta_imagen:
            self.imagen.cargar_desde_archivo(ruta_imagen)
            self.actualizar_estado_botones()

    def guardar_imagen(self):
        if self.imagen.esta_vacia():
            return
        ruta_guardado = filedialog.asksaveasfilename(
            title="Guardar imagen",
            defaultextension=".png",
            filetypes=(
                ("PNG", "*.png"),
                ("JPEG", "*.jpg;*.jpeg"),
                ("Todos los archivos", "*.*")
            )
        )
        if ruta_guardado:
            self.imagen.guardar(ruta_guardado)
            pygame.display.set_caption(f"Editor de Imágenes - Imagen guardada en: {ruta_guardado}")

    def aplicar_filtro(self, nombre_filtro):
        self.mostrar_barra_carga(f"Aplicando [{nombre_filtro}]...")
        pygame.event.pump()
        self.editor.aplicar_operacion('filtro', nombre_filtro)
        self.actualizar_estado_botones()

    def mostrar_barra_carga(self, mensaje="Procesando..."):
        texto = self.fuente_grande.render(mensaje, True, self.COLOR_TEXTO)
        padding = 40
        ancho_barra = max(300, texto.get_width() + padding)
        barra_rect = pygame.Rect(self.ancho//2 - ancho_barra//2, self.alto//2 - 20, ancho_barra, 40)
        self.pantalla.fill(self.COLOR_FONDO)
        pygame.draw.rect(self.pantalla, (100, 160, 210), barra_rect, border_radius=10)
        self.pantalla.blit(texto, (self.ancho//2 - texto.get_width()//2, self.alto//2 - texto.get_height()//2))
        pygame.display.flip()

    def obtener_imagen_escalada(self):
        if not self.imagen.esta_vacia():
            margen = max(10, int(self.ancho * 0.02))
            ancho_boton = max(120, int(self.ancho * 0.18))
            titulo_h = 50
            area_imagen = pygame.Rect(margen*2 + ancho_boton, titulo_h + margen, self.ancho - (margen*3 + ancho_boton), self.alto - (titulo_h + margen*2))
            if (self.imagen.version != self.cache_version or self.cache_size != (self.ancho, self.alto)):
                img_surface = self.imagen.imagen_pygame
                img_rect = img_surface.get_rect()
                if img_rect.width > area_imagen.width or img_rect.height > area_imagen.height:
                    scale = min(
                        area_imagen.width / img_rect.width, 
                        area_imagen.height / img_rect.height
                    )
                    new_size = (int(img_rect.width * scale), int(img_rect.height * scale))
                    self.imagen_escalada = pygame.transform.smoothscale(img_surface, new_size)
                else:
                    self.imagen_escalada = img_surface
                self.cache_version = self.imagen.version
                self.cache_size = (self.ancho, self.alto)
            return self.imagen_escalada, area_imagen
        return None, None

    def dibujar_interfaz(self):
        self.pantalla.fill(self.COLOR_FONDO)
        self.crear_botones()
        self.actualizar_estado_botones()
        # Título arriba
        titulo = self.fuente_titulo.render("Editor de Imágenes POO", True, self.COLOR_TEXTO)
        self.pantalla.blit(titulo, (self.ancho // 2 - titulo.get_width()//2, 10))
        # Botones a la izquierda con scroll
        menu_x = 0
        menu_y = 50
        menu_w = max(120, int(self.ancho * 0.18)) + max(10, int(self.ancho * 0.02)) * 2
        menu_h = self.alto - 50
        clip_rect = pygame.Rect(menu_x, menu_y, menu_w, menu_h)
        self.pantalla.set_clip(clip_rect)
        for boton in self.botones:
            color = boton['color']
            texto_color = self.COLOR_TEXTO
            if boton.get('es_titulo', False):
                pygame.draw.rect(self.pantalla, color, boton['rect'], border_radius=5)
            else:
                mouse_pos = pygame.mouse.get_pos()
                if not boton.get('enabled', True):
                    color = (150, 150, 150)
                    texto_color = (200, 200, 200)
                elif boton['rect'].collidepoint(mouse_pos):
                    color = self.COLOR_BOTON_HOVER
                pygame.draw.rect(self.pantalla, color, boton['rect'], border_radius=5)
            texto = self.fuente.render(boton['texto'], True, texto_color)
            pos_x = boton['rect'].x + (boton['rect'].width - texto.get_width()) // 2
            pos_y = boton['rect'].y + (boton['rect'].height - texto.get_height()) // 2
            self.pantalla.blit(texto, (pos_x, pos_y))
        self.pantalla.set_clip(None)
        # Barra de scroll visual
        if self.menu_scroll_max > 0:
            barra_x = menu_x + menu_w - 8
            barra_y = menu_y
            barra_w = 6
            barra_h = menu_h
            proporcion = menu_h / (menu_h + self.menu_scroll_max)
            alto_scroll = max(30, int(barra_h * proporcion))
            pos_scroll = int(barra_y + (barra_h - alto_scroll) * (self.menu_scroll / self.menu_scroll_max))
            pygame.draw.rect(self.pantalla, (60, 60, 60), (barra_x, barra_y, barra_w, barra_h), border_radius=3)
            pygame.draw.rect(self.pantalla, (180, 180, 200), (barra_x, pos_scroll, barra_w, alto_scroll), border_radius=3)
        # Área de imagen
        img_surface, area_imagen = self.obtener_imagen_escalada()
        if self.imagen.esta_vacia():
            if area_imagen:
                color_zona = self.COLOR_ZONA_ARRATRE
                pygame.draw.rect(self.pantalla, color_zona, area_imagen, border_radius=10)
                pygame.draw.rect(self.pantalla, (100, 150, 200), area_imagen, 3, border_radius=10)
                texto_o = self.fuente.render("Usa el botón 'Abrir imagen'", True, (180, 180, 220))
                self.pantalla.blit(texto_o, 
                                  (area_imagen.x + (area_imagen.width - texto_o.get_width())//2, 
                                   area_imagen.y + area_imagen.height//2))
        else:
            if img_surface and area_imagen:
                img_rect = img_surface.get_rect(center=area_imagen.center)
                self.pantalla.blit(img_surface, img_rect)
                info = self.fuente.render(
                    f"Tamaño: {self.imagen.imagen_pil.size[0]}x{self.imagen.imagen_pil.size[1]} | " 
                    f"Archivo: {os.path.basename(self.imagen.ruta_original)}", 
                    True, self.COLOR_TEXTO
                )
                self.pantalla.blit(info, (area_imagen.x + 10, area_imagen.bottom - 30))
        pygame.display.flip()

    def actualizar_estado_botones(self):
        for boton in self.botones:
            texto = boton['texto']
            # Botones de edición
            if texto == "Deshacer":
                boton['enabled'] = bool(self.imagen.historial)
            elif texto == "Rehacer":
                boton['enabled'] = bool(self.imagen.rehacer_pila)
            elif texto in [
                "Guardar", "Escala de Grises", "Rotar 90°", "Rotar -90°",
                "Redimensionar...", "Espejar H", "Espejar V", "Brillo +", "Brillo -"
            ]:
                boton['enabled'] = not self.imagen.esta_vacia()
            # Botones de filtros
            elif texto in [
                "Desenfoque", "Bordes", "Realzar", "Sepia", "Invertir", "Binarizar"
            ]:
                boton['enabled'] = not self.imagen.esta_vacia()
            # Botones de archivo y títulos siempre habilitados/deshabilitados según corresponda
            elif boton.get('es_titulo', False):
                boton['enabled'] = False
            elif texto == "Abrir imagen":
                boton['enabled'] = True
            else:
                # Por defecto, deshabilitar si no hay imagen
                boton['enabled'] = not self.imagen.esta_vacia()

    def redimensionar_dialogo(self):
        if self.imagen.esta_vacia():
            return
        ancho_actual, alto_actual = self.imagen.imagen_pil.size
        ancho = simpledialog.askinteger("Redimensionar", f"Nuevo ancho (actual: {ancho_actual})", parent=self.root_tk, minvalue=1)
        if ancho is None:
            return
        alto = simpledialog.askinteger("Redimensionar", f"Nuevo alto (actual: {alto_actual})", parent=self.root_tk, minvalue=1)
        if alto is None:
            return
        self.editor.redimensionar_personalizado(ancho, alto)
        self.actualizar_estado_botones()

    def ejecutar(self):
        ejecutando = True
        self.drag_active = False
        self.actualizar_estado_botones()
        while ejecutando:
            mouse_pos = pygame.mouse.get_pos()
            for evento in pygame.event.get():
                if evento.type == pygame.QUIT:
                    ejecutando = False
                elif evento.type == pygame.VIDEORESIZE:
                    self.ancho, self.alto = evento.w, evento.h
                    self.pantalla = pygame.display.set_mode((self.ancho, self.alto), pygame.RESIZABLE)
                elif evento.type == pygame.MOUSEBUTTONDOWN and evento.button == 1:
                    for boton in self.botones:
                        if (boton['rect'].collidepoint(mouse_pos) and 
                            boton.get('enabled', True) and 
                            boton.get('accion')):
                            boton['accion']()
                            self.actualizar_estado_botones()
                elif evento.type == pygame.MOUSEWHEEL:
                    if evento.y != 0:
                        self.menu_scroll -= evento.y * 30
                        self.menu_scroll = max(0, min(self.menu_scroll, self.menu_scroll_max))
            self.dibujar_interfaz()
        pygame.quit()
        import sys
        sys.exit()
