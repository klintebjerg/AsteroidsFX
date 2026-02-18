module Player {
    requires Common;
    requires jdk.jshell;
    provides dk.sdu.cbse.common.services.IGamePluginService with dk.sdu.cbse.PlayerPlugin;
}