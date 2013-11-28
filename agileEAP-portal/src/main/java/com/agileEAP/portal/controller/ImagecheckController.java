package com.agileEAP.portal.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/validcode")
public class ImagecheckController {
	
	private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
	
	@RequestMapping(value = "generate")
	public void getImage(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
        int width = 60;
        int height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g.setColor(getRandColor(200, 250));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(30 + random.nextInt(130),
                                 30 + random.nextInt(130),
                                 30 + random.nextInt(130)));
            g.drawString(rand, 13 * i + 6, 16);
        }
        HttpSession session = request.getSession();
        
        session.setAttribute("rand", sRand);
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
	//判断验证码是否正确
	@RequestMapping(value = "validate")
	@ResponseBody
	public boolean checkconfirmCode(@RequestParam("confirmCode") String confirmCode,HttpServletRequest request){
		String rand=request.getSession().getAttribute("rand").toString();
		return confirmCode.equals(rand);
	}
}
