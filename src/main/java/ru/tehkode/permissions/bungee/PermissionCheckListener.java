package ru.tehkode.permissions.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.tehkode.permissions.PermissionUser;

/**
 * Bungee PEx
 * Copyright (C) 2015 Max Lee (https://github.com/Phoenix616/)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PermissionCheckListener implements Listener {

    private final PermissionsEx plugin;

    public PermissionCheckListener(PermissionsEx plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPermissionCheck(PermissionCheckEvent event) {
        if(!event.hasPermission() && event.getSender() instanceof ProxiedPlayer) {
            PermissionUser pu = plugin.getPermissionsManager().getUser((ProxiedPlayer) event.getSender());
            if(pu != null) {
                event.setHasPermission(pu.has(event.getPermission()));
            }
        }
        
    }
}
