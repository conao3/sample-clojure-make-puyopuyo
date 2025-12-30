# sample-clojure-make-puyopuyo

A step-by-step tutorial for building a Puyo Puyo puzzle game using ClojureScript and Reagent.

## Overview

This project demonstrates how to create an interactive browser-based Puyo Puyo game from scratch. The tutorial is organized into progressive sections, each introducing new concepts and functionality.

## Features

- Browser-based puzzle game built with ClojureScript
- Reactive UI using Reagent (ClojureScript wrapper for React 19)
- Hot-reloading development environment with Figwheel
- Native compilation support via GraalVM

## Prerequisites

- [Clojure CLI](https://clojure.org/guides/install_clojure)
- [Node.js](https://nodejs.org/) with pnpm
- [GraalVM](https://www.graalvm.org/) (optional, for native compilation)

## Getting Started

Navigate to a section directory to work through the tutorial:

```bash
cd sections/section02
pnpm install
```

### Development

Start the development server with hot-reloading:

```bash
make repl-cljs
```

Open your browser to `http://localhost:9500` to see the application.

### Testing

```bash
make test
```

## Project Structure

```
sections/
  section01/    # Basic Clojure project setup
  section02/    # Figwheel + Reagent integration
    src/        # ClojureScript source files
    resources/  # Static assets and HTML
    dev/        # Development configuration
```

## Tutorial Stages

The tutorial progresses through the following stages:

1. Introduction
2. Development environment setup
3. Creating the game stage
4. Displaying Puyo pieces
5. Implementing free fall
6. Clearing matched Puyos
7. All-clear bonus
8. Player-controlled piece dropping
9. Fast drop with arrow key
10. Horizontal movement
11. Rotation mechanics
12. Next piece preview
13. Score display
14. Game over screen
15. Mobile device support

## License

Images in the `sega-img` folder are distributed under `SEGA_License.txt`. See https://edu.monaca.io/puyo for details.

All other files are licensed under Apache-2.0. See [LICENSE](LICENSE) for details.
