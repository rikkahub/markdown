# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run all tests (JVM, JS, WASM, native)
./gradlew check --stacktrace

# JVM tests only (fastest iteration)
./gradlew jvmTest

# Run a single test
./gradlew jvmTest --tests org.intellij.markdown.CommonMarkSpecTest.testTabsExample1

# Run file-based tests (HTML generator / parser output tests)
./gradlew fileBasedTest

# Performance tests (excluded from standard check)
./gradlew performanceTest

# macOS/iOS native (macOS CI only)
./gradlew macosX64Test iosX64Test

# Regenerate CommonMark and GFM spec compliance tests
./gradlew generateAllTests
```

## Architecture

This is a **Kotlin Multiplatform Markdown processor** that targets JVM, JS, WASM, and multiple native platforms (Linux, macOS, Windows, iOS, watchOS, tvOS). The processing pipeline has five stages:

1. **Lexing** — `MarkdownLexer` tokenizes raw text using JFlex-generated lexers (`.flex` files). These are JVM-generated but compiled to all targets.
2. **Block parsing** — `MarkerProcessor` applies a rollback-free state machine. Each block type is a `MarkerBlock` that responds to tokens with `ProcessingResult` (continue / close / drop). No backtracking.
3. **Inline parsing** — `SequentialParserManager` runs inline parsers in priority order (backticks before emphasis, etc.). Each parser claims ranges and passes remaining text to the next.
4. **AST construction** — Parsed ranges become `ASTNode` trees.
5. **HTML generation** — `HtmlGenerator` visits the AST using the visitor pattern; each node type is handled by a `GeneratingProvider`.

### Flavour Descriptor Pattern

All extensibility goes through `MarkdownFlavourDescriptor`:
- `markerProcessorFactory` — customize block parsing
- `sequentialParserManager.getParserSequence()` — inline parser order (order = priority)
- `createHtmlGeneratingProviders()` — map node types to HTML output

Provided flavours: `CommonMarkFlavourDescriptor` (base), `GFMFlavourDescriptor` (tables, strikethrough, autolinks), `SFMFlavourDescriptor` (JetBrains internal).

### Key Packages

| Package | Purpose |
|---|---|
| `org.intellij.markdown.parser` | Block parsing: `MarkdownParser`, `MarkerProcessor`, `MarkerBlock` impls |
| `org.intellij.markdown.parser.sequentialparsers` | Inline parsers (code spans, emphasis, links, images) |
| `org.intellij.markdown.lexer` | Tokenization; `.flex` grammar files |
| `org.intellij.markdown.flavours` | Flavour descriptors for CommonMark, GFM, SFM |
| `org.intellij.markdown.ast` | `ASTNode` interface and visitor infrastructure |
| `org.intellij.markdown.html` | `HtmlGenerator`, `GeneratingProvider` impls, XSS-safe link handling |

### Test Structure

- `commonTest` — CommonMark and GFM spec compliance tests (all platforms); auto-generated via `generateAllTests`
- `fileBasedTest` — compares `.md` input against expected `.txt` HTML/parser output in `src/fileBasedTest/resources/data/`
- `jvmTest` — performance benchmarks (not in standard `check`)

### Updating the Lexer

If `.flex` grammar files change, regenerate using JFlex and commit the resulting Kotlin source. See README.md for the exact JFlex invocation.
