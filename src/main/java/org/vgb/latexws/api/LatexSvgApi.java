package org.vgb.latexws.api;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.scilab.forge.jlatexmath.DefaultTeXFont;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.cyrillic.CyrillicRegistration;
import org.scilab.forge.jlatexmath.greek.GreekRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

@RestController
@RequestMapping("/api/latex/svg")
public class LatexSvgApi {
    private static final Logger logger = LoggerFactory.getLogger(LatexSvgApi.class);

    @Operation(summary = "Convert LaTeX formula to SVG")
    @GetMapping(value = "/", produces = "image/svg+xml")
    public byte[] convert(
            @RequestParam
            String formula,

            //@RequestParam(required = false)
            //Integer style,

            @RequestParam(required = false, defaultValue = "20")
            Float size,

            //@RequestParam(required = false)
            //String fgColor,

            //@RequestParam(required = false)
            //Float widthUnit,

            //@RequestParam(required = false)
            //Float textwidth,

            @RequestParam(required = false, defaultValue = "0")
            Integer insets,

            @RequestParam(required = false, defaultValue = "true")
            Boolean fontAsShapes
        ) throws IOException {

        return toSVG(formula, size, insets, fontAsShapes);
    }

    public static byte[] toSVG(String latex, float size, int insets, boolean fontAsShapes) throws IOException {
        logger.info("latex: " + latex);

        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);
        SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(document);

        SVGGraphics2D g2 = new SVGGraphics2D(ctx, fontAsShapes);

        DefaultTeXFont.registerAlphabet(new CyrillicRegistration());
        DefaultTeXFont.registerAlphabet(new GreekRegistration());

        TeXFormula formula = new TeXFormula(latex);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, size);
        icon.setInsets(new Insets(insets, insets, insets, insets));
        g2.setSVGCanvasSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        g2.setColor(Color.white);
        g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());

        JLabel jl = new JLabel();
        jl.setForeground(new Color(0, 0, 0));
        icon.paintIcon(jl, g2, 0, 0);

        boolean useCSS = true;
        ByteArrayOutputStream svgs = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(svgs, "UTF-8");
        g2.stream(out, useCSS);
        svgs.flush();
        svgs.close();
        return svgs.toByteArray();
    }
}
