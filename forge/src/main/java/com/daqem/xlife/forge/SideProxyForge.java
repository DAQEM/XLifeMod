package com.daqem.xlife.forge;

import com.daqem.xlife.XLife;
import com.daqem.xlife.client.XLifeClient;

public class SideProxyForge {

    SideProxyForge() {
        XLife.initCommon();
    }



    public static class Server extends SideProxyForge {
        Server() {
        }

    }

    public static class Client extends SideProxyForge {
        Client() {
            XLifeClient.initClient();
        }
    }
}
