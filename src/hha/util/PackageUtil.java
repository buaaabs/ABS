package hha.util;

import java.util.List;

import android.annotation.SuppressLint;
import dalvik.system.DexClassLoader;

public class PackageUtil {

	public static DexClassLoader loader;
	/**
	 * 获取某包下（包括该包的所有子包）所有类
	 * @param packageName 包名
	 * @return 类的完整名称
	 */
	public static List<String> getClassName(String packageName) {
		return getClassName(packageName, true);
	}

	/**
	 * 获取某包下所有类
	 * @param packageName 包名
	 * @param childPackage 是否遍历子包
	 * @return 类的完整名称
	 */
	@SuppressLint("NewApi")
	public static List<String> getClassName(String packageName, boolean childPackage) {
		
		
		return null;
	}

}
