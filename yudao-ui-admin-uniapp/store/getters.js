const getters = {
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  roles: state => state.user.roles,
  permissions: state => state.user.permissions,
  // 数据字典
  dict_datas: state => state.dict.dictDatas
}
export default getters
