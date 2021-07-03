package com.wsw.cospahm.slice;

import com.koushikdutta.quack.QuackContext;
import com.wsw.cospahm.ResourceTable;
import com.wsw.cospahm.event.RefreshEngineTestMessageObservable;
import com.wsw.cospahm.event.RefreshEngineTestMessageObserver;
import com.wsw.cospahm.utils.HandlerUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;

import java.io.IOException;

public class MainAbilitySlice extends AbilitySlice implements RefreshEngineTestMessageObserver {
    private Text tvContent;
    private final String enter = "\r\n";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        HandlerUtils.init();
        tvContent = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        RefreshEngineTestMessageObservable.getInstance().registerObserver(this);

        HandlerUtils.runWorkThread(() -> {
            ResourceManager resourceManager = getApplicationContext().getResourceManager();
            RawFileEntry rawFileEntry = resourceManager.getRawFileEntry(String.format("resources/rawfile/%s", "bechmarks.js"));
            try {
                QuackContext quackContext = QuackContext.create();
                Resource resource = rawFileEntry.openRawFile();
                int length = resource.available();
                byte[] data = new byte[length];
                resource.read(data, 0, length);
                String bechmarks = new String(data);
                resource.close();
                quackContext.getGlobalObject().set("Foo", Foo.class);
                HandlerUtils.runOnUiThread(() -> tvContent.append("★Quack开始测试" + enter));
                quackContext.evaluate(bechmarks);
                HandlerUtils.runOnUiThread(() -> tvContent.append(enter + "★Quack完成测试" + enter));
                quackContext.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onRefreshEngineTestMessage(String message) {
        if (null == tvContent)
            tvContent = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        int titleIndex = message.indexOf("★");
        if (titleIndex >= 0) {
            tvContent.append(message);
            if (null != tvContent.getText() && !tvContent.getText().isEmpty()) {
                tvContent.append(enter);
            }
        } else {
            if (null != tvContent.getText() && !tvContent.getText().isEmpty()) {
                tvContent.append(enter);
            }
            tvContent.append(message);
        }
    }

    public static class Foo {
        public void print(Object s) {
            String msg = s.toString();
            HandlerUtils.runOnUiThread(() -> {
                RefreshEngineTestMessageObservable.getInstance().notifyObservers(msg);
            });
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        HandlerUtils.quit();
        RefreshEngineTestMessageObservable.getInstance().unRegisterObserver(this);
    }
}
