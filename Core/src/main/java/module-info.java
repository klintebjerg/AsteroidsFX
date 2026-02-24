module Core {
    requires Common;
    requires javafx.graphics;
    uses dk.sdu.cbse.common.services.IGamePluginService;
    opens dk.sdu.cbse to javafx.graphics;
}