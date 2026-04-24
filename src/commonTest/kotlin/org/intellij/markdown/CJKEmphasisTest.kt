package org.intellij.markdown

import kotlin.test.Test

class CJKEmphasisTest : SpecTest(org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor()) {
    @Test
    fun testBoldWithQuotesBeforeCJK() = doTest(
        markdown = "**'确实'**厉害啊\n",
        html = "<p><strong>'确实'</strong>厉害啊</p>\n"
    )

    @Test
    fun testBoldWithDoubleQuotesBeforeCJK() = doTest(
        markdown = "**\"确实\"**厉害啊\n",
        html = "<p><strong>&quot;确实&quot;</strong>厉害啊</p>\n"
    )

    @Test
    fun testCJKBeforeBoldWithQuotes() = doTest(
        markdown = "厉害啊**'确实'**\n",
        html = "<p>厉害啊<strong>'确实'</strong></p>\n"
    )

    @Test
    fun testBasicCJKBold() = doTest(
        markdown = "这很**厉害**啊\n",
        html = "<p>这很<strong>厉害</strong>啊</p>\n"
    )

    @Test
    fun testBoldWithCurlyQuotesBeforeCJK() = doTest(
        markdown = "**‘确实’**厉害啊\n",
        html = "<p><strong>‘确实’</strong>厉害啊</p>\n"
    )

    @Test
    fun testItalicWithQuotesBeforeCJK() = doTest(
        markdown = "*'确实'*厉害啊\n",
        html = "<p><em>'确实'</em>厉害啊</p>\n"
    )

    @Test
    fun testTwoBoldSegmentsWithQuotes() = doTest(
        markdown = "**\"渴望\"**和**\"行动力\"**的能量\n",
        html = "<p><strong>&quot;渴望&quot;</strong>和<strong>&quot;行动力&quot;</strong>的能量</p>\n"
    )
}
