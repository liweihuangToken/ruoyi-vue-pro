import request from '@/utils/request'
import qs from 'qs'

// 创建配货综合订单信息
export function createComprehensiveOrderInfo(data) {
  return request({
    url: '/distribution/comprehensive-order-info/create',
    method: 'post',
    data: data
  })
}

// 更新配货综合订单信息
export function updateComprehensiveOrderInfo(data) {
  return request({
    url: '/distribution/comprehensive-order-info/update',
    method: 'put',
    data: data
  })
}

// 删除配货综合订单信息
export function deleteComprehensiveOrderInfo(id) {
  return request({
    url: '/distribution/comprehensive-order-info/delete?id=' + id,
    method: 'delete'
  })
}

// 获得配货综合订单信息
export function getComprehensiveOrderInfo(id) {
  return request({
    url: '/distribution/comprehensive-order-info/get?id=' + id,
    method: 'get'
  })
}

// 获得配货综合订单信息分页
export function getComprehensiveOrderInfoPage(query) {
  return request({
    url: '/distribution/comprehensive-order-info/page',
    method: 'get',
    params: query
  })
}

// 导出配货综合订单信息 Excel
export function exportComprehensiveOrderInfoExcel(query) {
  return request({
    url: '/distribution/comprehensive-order-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}


// 导出配货综合订单信息导入模板 Excel
export function exportComprehensiveOrderInfoImportTemplateExcel(query) {
  return request({
    url: '/distribution/comprehensive-order-info/get-import-template',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 创建配货综合订单信息
export function assignOrderInfoCode(ids) {
  return request({
    url: '/distribution/comprehensive-order-info/assign-order-code?ids=' + ids,
    method: 'put'
  })
}
