package org.intellij.markdown

import kotlin.test.Test

class GfmTest: SpecTest(org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor()) {
    @Test
    fun testAutolinkInsideATag() = doTest(
        markdown = "<a href=\"https://jb.gg\">https://www.jb.gg/?q=19</a>",
        html = "<p><a href=\"https://jb.gg\"><a href=\"https://www.jb.gg/?q=19\">https://www.jb.gg/?q=19</a></a></p>"
    )

    @Test
    fun testTableInterruptsParagraph() = doTest(
        markdown = "some text\n| A | B |\n|---|---|\n| c | d |\n",
        html = "<p>some text</p><table><thead><tr><th>A</th><th>B</th></tr></thead><tbody><tr><td>c</td><td>d</td></tr></tbody></table>"
    )

    @Test
    fun testTableInsideListItem() = doTest(
        markdown = "1. text\n\n   | A | B |\n   |---|---|\n   | c | d |\n",
        html = "<ol><li><p>text</p><table><thead><tr><th>A</th><th>B</th></tr></thead><tbody><tr><td>c</td><td>d</td></tr></tbody></table></li></ol>"
    )

    @Test
    fun testTableInterruptsParagraphInsideListItem() = doTest(
        markdown = "1. text\n   | A | B |\n   |---|---|\n   | c | d |\n",
        html = "<ol><li>text<table><thead><tr><th>A</th><th>B</th></tr></thead><tbody><tr><td>c</td><td>d</td></tr></tbody></table></li></ol>"
    )
}