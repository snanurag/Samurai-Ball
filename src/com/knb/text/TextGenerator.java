package com.knb.text;
/*
 * This class could be used when the text displayed at UI also consists of the 3D objects. But it should be taken in consideration that it will consume a lot of loading time since all the text consists of object files
 */

//package com.knb.text;
//
//import min3d.Shared;
//import min3d.core.Object3d;
//import min3d.parser.IParser;
//import min3d.parser.Parser;
//
//import com.knb.constants.SBStore;
//
//public class TextGenerator {
//
//	private static volatile boolean isTextLoaded = false;
//
//	private static Object3d p;
//	private static Object3d l;
//	private static Object3d a;
//	private static Object3d y;
//	private static Object3d b;
//	private static Object3d e;
//	private static Object3d s;
//	private static Object3d t;
//	private static Object3d c;
//	private static Object3d o;
//	private static Object3d r;
//	private static Object3d g;
//	private static Object3d m;
//	private static Object3d v;
//
//	public static void loadAllText() {
//
//		if (!isTextLoaded) {
//			IParser p = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			p.parse();
//
//			IParser l = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_l_obj", true);
//			l.parse();
//
//			IParser a = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			a.parse();
//
//			IParser y = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			y.parse();
//
//			IParser b = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			b.parse();
//
//			IParser e = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			e.parse();
//
//			IParser s = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			s.parse();
//
//			IParser t = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			t.parse();
//
//			IParser c = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			c.parse();
//
//			IParser o = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			o.parse();
//
//			IParser r = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			r.parse();
//
//			IParser g = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			g.parse();
//
//			IParser m = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			m.parse();
//
//			IParser v = Parser.createParser(Parser.Type.OBJ, Shared.context()
//					.getResources(), "com.knb:raw/letter_p_obj", true);
//			v.parse();
//
//			TextGenerator.p = p.getParsedObject();
//			TextGenerator.l = l.getParsedObject();
//			TextGenerator.a = a.getParsedObject();
//			TextGenerator.y = y.getParsedObject();
//			TextGenerator.b = b.getParsedObject();
//			TextGenerator.e = e.getParsedObject();
//			TextGenerator.s = s.getParsedObject();
//			TextGenerator.t = t.getParsedObject();
//			TextGenerator.c = c.getParsedObject();
//			TextGenerator.o = o.getParsedObject();
//			TextGenerator.r = r.getParsedObject();
//			TextGenerator.g = g.getParsedObject();
//			TextGenerator.m = m.getParsedObject();
//			TextGenerator.v = v.getParsedObject();
//
//			isTextLoaded = true;
//		}
//
//	}
//
//	/**
//	 * 
//	 * @param text
//	 * @return
//	 */
//	public static Object3d[] getText(String text, float scale) {
//		char[] txtChar = text.toLowerCase().toCharArray();
//
//		Object3d[] txtObject3d = new Object3d[txtChar.length];
//
//		for (int i = 0; i < txtChar.length; i++) {
//			switch (txtChar[i]) {
//			case 'p':
//				txtObject3d[i] = p.clone();
//				break;
//			case 'l':
//				txtObject3d[i] = l.clone();
//				break;
//			case 'a':
//				txtObject3d[i] = a.clone();
//				break;
//			case 'y':
//				txtObject3d[i] = y.clone();
//				break;
//			case 'b':
//				txtObject3d[i] = b.clone();
//				break;
//			case 'e':
//				txtObject3d[i] = e.clone();
//				break;
//			case 's':
//				txtObject3d[i] = s.clone();
//				break;
//			case 't':
//				txtObject3d[i] = t.clone();
//				break;
//			case 'c':
//				txtObject3d[i] = c.clone();
//				break;
//			case 'o':
//				txtObject3d[i] = o.clone();
//				break;
//			case 'r':
//				txtObject3d[i] = r.clone();
//				break;
//			case 'g':
//				txtObject3d[i] = g.clone();
//				break;
//			case 'm':
//				txtObject3d[i] = m.clone();
//				break;
//			case 'v':
//				txtObject3d[i] = v.clone();
//				break;
//
//			}
//
//		}
//
//		setScale(txtObject3d, scale);
//		setPositionOfText(txtObject3d, txtChar, scale);
//		return txtObject3d;
//	}
//
//	/**
//	 * 
//	 * @param txtObject3d
//	 * @param txtChar
//	 */
//	private static void setPositionOfText(Object3d[] txtObject3d,
//			char[] txtChar, float scale) {
//
//		for (int i = 0; i < txtChar.length; i++) {
//
//			if (i > 0 && (txtChar[i - 1] == 'i' || txtChar[i - 1] == 'l')) {
//
//				txtObject3d[i].position().x = txtObject3d[i - 1].position().x
//						+ SBStore.DIST_BETWEEN_LETTERS_MIN * scale;
//			} else if (i > 0) {
//				txtObject3d[i].position().x = txtObject3d[i - 1].position().x
//						+ SBStore.DIST_BETWEEN_LETTERS_MAX * scale;
//			}
//		}
//	}
//
//	private static void setScale(Object3d[] txtObject3d, float scale) {
//		for (Object3d o : txtObject3d) {
//			o.scale().x = o.scale().y = o.scale().z = scale;
//		}
//	}
//
//}
