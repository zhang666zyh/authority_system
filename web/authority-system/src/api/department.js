import http from "@/utils/request";


export default {
    /**
     * 查询部门列表
     * @param params
     * @returns
     */
    async getDepartmentList(params) {
        return await http.get("/api/department/list", params);
    },

    /**
     * 查询所属部门(父部门)列表
     * @param 
     * @returns
     */
    async getParentsTreeList() {
        return await http.get("/api/department/parent/list");
    },

    /**
     * 新增部门
     * @param params
     * @returns
     */
    async addDept(params) {
        return await http.post("/api/department/add", params);
    },

    /**
     * 编辑部门
     * @param params
     * @returns
     */
    async updateDept(params) {
        return await http.put("/api/department/update", params);
    },

    /**
     * 查询当前部门下是否有子部门 或 用户
     * @param params
     * @returns
     */
    async checkHasParentDepartment(params) {
        return await http.getRestApi("/api/department/check", params);
    },

    /**
     * 删除部门
     * @param params
     * @returns
     */
    async deleteDeptById(params) {
        return await http.delete("/api/department/delete", params);
    },
}
