module Core {
    requires Common;
    requires javafx.graphics;
    exports dk.sdu.cbse;
    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessingService;
    opens dk.sdu.cbse to javafx.graphics;
}