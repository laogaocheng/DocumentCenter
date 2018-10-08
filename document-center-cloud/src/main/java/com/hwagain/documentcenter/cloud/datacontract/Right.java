package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class Right  implements Serializable{

	private static final long serialVersionUID = 1L;

	private byte view_right;
	private byte write_right;
	private byte move_right;
	private byte share_right;
	private byte delete_right;
	private byte manage_right;
	private byte create_right;
	private byte down_right;
	
	public byte getView_right() {
		return view_right;
	}
	public void setView_right(byte view_right) {
		this.view_right = view_right;
	}
	public byte getWrite_right() {
		return write_right;
	}
	public void setWrite_right(byte write_right) {
		this.write_right = write_right;
	}
	public byte getMove_right() {
		return move_right;
	}
	public void setMove_right(byte move_right) {
		this.move_right = move_right;
	}
	public byte getShare_right() {
		return share_right;
	}
	public void setShare_right(byte share_right) {
		this.share_right = share_right;
	}
	public byte getDelete_right() {
		return delete_right;
	}
	public void setDelete_right(byte delete_right) {
		this.delete_right = delete_right;
	}
	public byte getManage_right() {
		return manage_right;
	}
	public void setManage_right(byte manage_right) {
		this.manage_right = manage_right;
	}
	public byte getCreate_right() {
		return create_right;
	}
	public void setCreate_right(byte create_right) {
		this.create_right = create_right;
	}
	public byte getDown_right() {
		return down_right;
	}
	public void setDown_right(byte down_right) {
		this.down_right = down_right;
	}
}
