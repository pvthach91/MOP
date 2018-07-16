package com.brian.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaders {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		fileReader("C:\\iptest.txt");

	}
	public static void fileReader(String filePath) {
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			long i=0;
			while ((tempString = reader.readLine()) != null) {
				//System.out.print(tempString);
				String[] arr= tempString.split(" ");
				//System.out.println(arr.length);
				int s=0;
				String sip="",eip="",des="";
				for(int j=0;j<arr.length;j++){
					if(arr[j].length()>0){
						s++;
						if(s==1){
							sip = arr[j];
						}else if(s==2){
							eip=arr[j];
						}else{
							des+=arr[j];
						}
					}
				}
				System.out.println(sip+"-"+eip+"-"+des);
				//i++;
				//System.out.println("");
			}
			reader.close();
			///////////////////////////////
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
}
