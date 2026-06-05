# Paint3D Mario Edition

A Mario-themed JavaFX paint application built for a UTM assignment using an MVC + Observer architecture.

## Overview

This project provides an interactive drawing canvas with multiple shape tools, styling controls, selection/move support, undo/redo, clipboard-style editing, PNG import/export, custom cursor/splash assets, and background/button audio.

Despite the repository name, the current implementation is a 2D drawing app.

## Core Features

- **Shape tools**: Circle, Rectangle, Square, Squiggle, Polyline, Triangle, Oval
- **Style controls**: fill toggle, line-thickness slider, live color picker
- **Selection mode**: select nearest visible shape and drag to reposition
- **Edit operations**: cut, copy, paste, delete
- **History controls**: undo/redo
- **File operations**: open PNG as canvas image, save canvas snapshot as PNG
- **Theming**: Mario-themed CSS, custom cursor, startup splash image
- **Audio**: looping background music + button click SFX (with graceful fallback when audio is unavailable)

## Technology Stack

- **Language**: Java 22
- **UI**: JavaFX (`controls`, `fxml`, `graphics`, `base`)
- **Build tool**: Maven Wrapper (`./mvnw`)
- **Module system**: JPMS (`module-info.java`)

## Repository Structure

```text
paint3d-mario-edition/
├── Assignment2/
│   ├── pom.xml
│   ├── src/main/java/
│   │   ├── module-info.java
│   │   ├── ca/utoronto/utm/assignment2/         # JavaFX template app (Hello*)
│   │   ├── ca/utoronto/utm/assignment2/paint/   # Main paint application
│   │   └── ca/utoronto/utm/assignment2/scribble/# Demo scribble app
│   └── src/main/resources/
│       ├── paint/mario-theme.css
│       ├── paint/music.wav
│       ├── paint/repo_420.png
│       ├── paint/repo_420_cursor.png
│       └── paint/repo_420_button_sound.wav
└── members.txt
```

## Architecture

The paint app (`ca.utoronto.utm.assignment2.paint`) is structured around:

- **MVC responsibilities**
  - `PaintModel`: canonical shape/canvas state, selection state, undo/redo/copy buffers
  - `View`: JavaFX composition root wiring controllers + UI components
  - `PaintPanel`, `ShapeChooserView`, `MenuView`, `ColourSelector`: concrete UI surfaces
  - `PaintController`, `ShapeChooserController`, `MenuController`: user-action handlers

- **Observer pattern**
  - `PaintModel` and `ShapeChooserModel` extend `Observable`
  - view components implement `Observer` and repaint/update from model notifications

- **Strategy pattern for mouse behavior**
  - `StrategyDecider` routes events to:
    - `NormalShapeHandler` (standard press-drag-release shapes)
    - `PolylineHandler` (multi-click polyline mode with right-click finalize)
    - `SelectionModeHandler` (select + drag)

- **Factory pattern**
  - `ShapeFactory` builds `Drawable` implementations from active tool settings.

## Key Components

- **Entry point for paint app**: `paint/Paint.java` (launches `View`)
- **Canvas renderer**: `paint/PaintPanel.java`
- **Shape abstractions**:
  - `Drawable` interface
  - Implementations: `Circle`, `Rectangle`, `Square`, `Triangle`, `Oval`, `Squiggle`, `Polyline`, `ImageShape`
- **Selection UX**: `SelectionRectangle`
- **Media helpers**: `BackgroundMusic`, `PopupImageMaker`

## User Interaction Flow

1. User selects tool/options in shape chooser panel.
2. Canvas mouse events are handled by `PaintController`.
3. `StrategyDecider` dispatches to the active interaction handler.
4. Handler mutates `PaintModel` (create/update/select/move shapes).
5. `PaintModel` notifies observers.
6. `PaintPanel` redraws all visible drawables and selection outlines.

## Build & Run

### Prerequisites

- JDK **22** installed and active in `JAVA_HOME`
- A system that can run JavaFX desktop apps

### Build

From `/tmp/workspace/mufferio/paint3d-mario-edition/Assignment2`:

```bash
./mvnw clean verify
```

### Run options

- **Run paint app (recommended)**: run `ca.utoronto.utm.assignment2.paint.Paint` from your IDE.
- **Maven JavaFX run** currently points to `HelloApplication` in `pom.xml`:

```bash
./mvnw javafx:run
```

## Menu Operations

- **File**
  - New: clears canvas/history
  - Open: loads a PNG as an `ImageShape`
  - Save: snapshots canvas to PNG
  - Exit: closes app
- **Edit**
  - Cut / Copy / Paste
  - Undo / Redo
  - Delete

## Assets & Theming

UI and branding are provided from `/src/main/resources/paint/`:

- `mario-theme.css` for colors and component styling
- `repo_420_cursor.png` custom cursor
- `repo_420.png` splash image
- `music.wav` background loop
- `repo_420_button_sound.wav` tool click sound

## Known Constraints

- No dedicated `src/test` suite is currently included.
- Build requires Java 22 (`maven-compiler-plugin` source/target set to `22`).
- Some classes (`Hello*`, `scribble/*`) appear to be scaffolding/demo code and are separate from the primary paint workflow.

## Troubleshooting

- **`invalid target release: 22`**
  - Cause: active JDK is below 22.
  - Fix: install/select JDK 22 and re-run Maven.

- **No audio output**
  - The app catches unavailable-audio-device conditions and continues without sound.
