import request from '@/utils/request'

// 创建配货供应商信息
export function createUpstreamInfo(data) {
  return request({
    url: '/distribution/upstream-info/create',
    method: 'post',
    data: data
  })
}

// 更新配货供应商信息
export function updateUpstreamInfo(data) {
  return request({
    url: '/distribution/upstream-info/update',
    method: 'put',
    data: data
  })
}

// 删除配货供应商信息
export function deleteUpstreamInfo(id) {
  return request({
    url: '/distribution/upstream-info/delete?id=' + id,
    method: 'delete'
  })
}

// 获得配货供应商信息
export function getUpstreamInfo(id) {
  return request({
    url: '/distribution/upstream-info/get?id=' + id,
    method: 'get'
  })
}

// 获得配货供应商信息分页
export function getUpstreamInfoPage(query) {
  return request({
    url: '/distribution/upstream-info/page',
    method: 'get',
    params: query
  })
}

// 导出配货供应商信息 Excel
export function exportUpstreamInfoExcel(query) {
  return request({
    url: '/distribution/upstream-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
