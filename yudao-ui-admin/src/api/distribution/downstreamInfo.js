import request from '@/utils/request'

// 创建配货客户信息
export function createDownstreamInfo(data) {
  return request({
    url: '/distribution/downstream-info/create',
    method: 'post',
    data: data
  })
}

// 更新配货客户信息
export function updateDownstreamInfo(data) {
  return request({
    url: '/distribution/downstream-info/update',
    method: 'put',
    data: data
  })
}

// 删除配货客户信息
export function deleteDownstreamInfo(id) {
  return request({
    url: '/distribution/downstream-info/delete?id=' + id,
    method: 'delete'
  })
}

// 获得配货客户信息
export function getDownstreamInfo(id) {
  return request({
    url: '/distribution/downstream-info/get?id=' + id,
    method: 'get'
  })
}

// 获得配货客户信息分页
export function getDownstreamInfoPage(query) {
  return request({
    url: '/distribution/downstream-info/page',
    method: 'get',
    params: query
  })
}

// 导出配货客户信息 Excel
export function exportDownstreamInfoExcel(query) {
  return request({
    url: '/distribution/downstream-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
