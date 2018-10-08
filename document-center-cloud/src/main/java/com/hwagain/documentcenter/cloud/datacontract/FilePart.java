package com.hwagain.documentcenter.cloud.datacontract;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Base64Utils;

public class FilePart implements Serializable {
	private static final long serialVersionUID = 1L;
	public static int BLOCK_SIZE = 8 * 1024 * 1024;

	public int partSize;
	public String partSha1;
	public byte[] data;
	public int partNumber;
	public String partUploadId;
	public String partCommitId;
	public String filename;

	public int getPartSize() {
		return partSize;
	}

	public void setPartSize(int partSize) {
		this.partSize = partSize;
	}

	public String getPartSha1() {
		return partSha1;
	}

	public void setPartSha1(String partSha1) {
		this.partSha1 = partSha1;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartUploadId() {
		return partUploadId;
	}

	public void setPartUploadId(String partUploadId) {
		this.partUploadId = partUploadId;
	}

	public String getPartCommitId() {
		return partCommitId;
	}

	public void setPartCommitId(String partCommitId) {
		this.partCommitId = partCommitId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PartInfo getPartInfo() {
		PartInfo pInfo = new PartInfo();
		pInfo.setPartSize(partSize);
		pInfo.setPartSha1(partSha1);
		return pInfo;
	}

	public static List<FilePart> getFilePartsInfo(String filename, InputStream stream) {
		List<FilePart> parts = new ArrayList<FilePart>();

		try {
			int size = -1;
			int partNumber = 0;
			do {
				byte[] buffer = new byte[BLOCK_SIZE];
				size = stream.read(buffer, 0, BLOCK_SIZE);
				if (size > 0) {
					byte[] data = new byte[size];
					System.arraycopy(buffer, 0, data, 0, size);

					MessageDigest digest = MessageDigest.getInstance("SHA1");
					digest.update(buffer, 0, size);
					byte[] sha1Bytes = digest.digest();
					String sha1 = Base64Utils.encodeToString(sha1Bytes);

					System.out.println(sha1);

					FilePart part = new FilePart();
					part.data = data;
					part.partSize = size;
					part.partSha1 = SafeUrlBase64Encode(sha1);
					part.partNumber = partNumber;
					part.filename = filename;

					parts.add(part);

					partNumber++;
				} else
					break;
			} while (true);

			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parts;
	}

	public static List<FilePart> getFilePartsInfo(String filename, byte[] fileData) {
		InputStream stream = new ByteArrayInputStream(fileData);
		return getFilePartsInfo(filename, stream);
	}

	public static List<FilePart> getFilePartsInfo(File file) {
		if (!file.exists())
			return null;
		
		try {
			InputStream stream = new FileInputStream(file);
			return getFilePartsInfo(file.getName(), stream);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String SafeUrlBase64Encode(String s) {
		return s.replace('+', '-').replace('/', '_').replace("=", "");
	}
}
