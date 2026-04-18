package io.github.loncra.framework.fasc.res.signtask;

import java.util.List;

public class SignTaskGetFileRes {
    private List<DocFileInfo> docs;

    public List<DocFileInfo> getDocs() {
        return docs;
    }

    public void setDocs(List<DocFileInfo> docs) {
        this.docs = docs;
    }

    public static class DocFileInfo {
        private String docId;
        private String docName;
        private String fileId;
        private String fddFileUrl;

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public String getDocName() {
            return docName;
        }

        public void setDocName(String docName) {
            this.docName = docName;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getFddFileUrl() {
            return fddFileUrl;
        }

        public void setFddFileUrl(String fddFileUrl) {
            this.fddFileUrl = fddFileUrl;
        }
    }

}
