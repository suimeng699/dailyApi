package com.suimeng.domain.pojo.image;

public class ImageParam {
    //可选	rsp_media_type	String	“url”：抠图结果以短期有效的url返回
    private String rsp_media_type = "url";
    //可选	nMask	bool	是否返回mask图，True只返回mask图，False返回结果图
    private boolean nMask = false;
    //可选	model_type	int	选择要使用的抠图模型，传0：表示使用人像抠图；传1：表示使用商品抠图；传2：表示使用图形抠图。若不传，模型内部会自动判断选择使用哪个模型
    private int model_type = -1;
    //可选	userboxes	string	图标类型可以添加用户交互框参数，坐标应当使用相对坐标，示例：[[[0.01, 0.814], [0.12, 0.814], [0.12, 0.96], [0.01, 0.96]]
    private String userboxes = null;
    //可选	blackwhite	bool	是否返回黑白图，True只返回黑白mask图，False返回四通道Mask图，默认为False
    private boolean blackwhite = false;
    //可选	nbox	bool	是否返回目标位置，True返回目标位置top_x,top_y,bottom_x,bottom_y,默认为False
    private boolean nbox = false;

    @Override
    public String toString() {
        return "{" +
                (nMask == false?"":("\\\"nMask\\\":\\\"" + nMask + "\\\", ")) +
                (model_type == -1 ? "" :("\\\"model_type\\\":" + model_type + ",")) +
                (userboxes == null ? "" :("\\\"userboxes\\\":\\\"" + userboxes + "\\\",")) +
                (blackwhite == false?"":("\\\"blackwhite\\\":\\\"" + blackwhite + "\\\","))+
                (nbox == false?"":("\\\"nbox\\\":\\\"" + nbox + "\\\",")) +
                "\\\"rsp_media_type\\\":\\\"" + rsp_media_type +"\\\"" +
                "}";
    }
}
