package com.kosi0917.textandfacerecognitionapp.Common.utils;

import com.kosi0917.textandfacerecognitionapp.Model.VK.Owner;
import com.kosi0917.textandfacerecognitionapp.Model.VK.WallItem;
import com.kosi0917.textandfacerecognitionapp.rest.model.response.ItemWithSendersResponse;

import java.util.List;

/**
 * Created by mozil on 28.01.2018.
 */

public class VkListHelper {
    public static List<WallItem> getWallList(ItemWithSendersResponse<WallItem> response) {
        List<WallItem> wallItems = response.items;

        for (WallItem wallItem : wallItems) {
            Owner sender = response.getSender(wallItem.getFromId());
            wallItem.setSenderName(sender.getFullName());
            wallItem.setSenderPhoto(sender.getPhoto());

            if (wallItem.getApiAttachments() != null) {
                wallItem.setAttachmentsString(Utils.convertAttachmentsToFontIcons(wallItem.getApiAttachments()));
            }

            if (wallItem.haveSharedRepost()) {
                Owner repostSender = response.getSender(wallItem.getSharedRepost().getFromId());
                wallItem.getSharedRepost().setSenderName(repostSender.getFullName());
                wallItem.getSharedRepost().setSenderPhoto(repostSender.getPhoto());

                wallItem.getSharedRepost().setAttachmentsString(Utils.convertAttachmentsToFontIcons(
                        wallItem.getSharedRepost().getApiAttachments()
                ));
            }
        }
        return wallItems;
    }
}
