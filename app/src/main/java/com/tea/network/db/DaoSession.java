package com.tea.network.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by jiangtea on 2020/6/6.
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig downloadEntityDaoConfig;

    private final DownloadEntityDao downloadEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downloadEntityDaoConfig = daoConfigMap.get(DownloadEntityDao.class).clone();
        downloadEntityDaoConfig.initIdentityScope(type);

        downloadEntityDao = new DownloadEntityDao(downloadEntityDaoConfig, this);

        registerDao(DownloadEntity.class, downloadEntityDao);
    }

    public void clear() {
        downloadEntityDaoConfig.getIdentityScope().clear();
    }

    public DownloadEntityDao getDownloadEntityDao() {
        return downloadEntityDao;
    }

}
