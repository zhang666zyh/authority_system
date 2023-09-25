import http from '@/utils/request'

/**
 * 用户登录方法
 * @param {*} data 
 * @returns 
 */
export async function login(data) {
  return await http.login("/api/user/login", data);
}

/**
 * 获取用户信息和权限信息
 * @param {*} token 
 * @returns 
 */
export async function getInfo(token) {
  return await http.get("/api/sysUser/getInfo");
}

/**
 * 退出登录
 * @returns 
 */
export async function logout(param) {
  return await http.post("/api/sysUser/logout", param);
}


/**
 * 获取用户菜单信息 
 */
export async function getMenuList() {
  return await http.get("/api/sysUser/getMenuList");
}

/**
* 查询用户列表
* @param params
* @returns
*/
export async function getUserList(params) {
  return await http.get("/api/user/list", params);
}

/**
* 添加用户
* @param params
* @returns
*/
export async function addUser(params) {
  return await http.post("/api/user/add", params);
}

/**
* 编辑用户
* @param params
* @returns
*/
export async function updateUser(params) {
  return await http.put("/api/user/update", params);
}

/**
* 删除用户
* @param params
* @returns
*/
export async function deleteUser(params) {
  return await http.delete("/api/user/delete", params);
}

/**
* 查询用户角色列表
* @param params
* @returns
*/
export async function getAssignRoleList(params) {
  return await http.get("/api/user/getRoleListForAssign", params);
}


/**
* 获取分配角色列表数据
* @param params
* @returns
*/
export async function getRoleIdByUserId(params) {
  return await http.getRestApi("/api/user/getRoleByUserId", params);
}

/**
* 分配角色
*/
export async function assignRoleSave(params) {
  return await http.post("/api/user/saveUserRole", params)
}

/**
* 刷新token
* @returns
*/
export async function refreshTokenApi(params) {
  return await http.post("/api/sysUser/refreshToken", params);
}