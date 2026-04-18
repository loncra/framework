package io.github.loncra.framework.fasc.res.archives;

import java.util.List;

public class ContactArchivedRes {

    /**
     * 归档ID列表
     */
    private List<ArchiveInfo> archives;

    public static class ArchiveInfo {

        /**
         * 归档ID
         */
        private String archivesId;

        public String getArchivesId() {
            return archivesId;
        }

        public void setArchivesId(String archivesId) {
            this.archivesId = archivesId;
        }
    }

    public List<ArchiveInfo> getArchives() {
        return archives;
    }

    public void setArchives(List<ArchiveInfo> archives) {
        this.archives = archives;
    }
}
