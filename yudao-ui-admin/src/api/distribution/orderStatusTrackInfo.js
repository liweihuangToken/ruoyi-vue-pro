import request from '@/utils/request'

// 创建配货订单状态跟踪
export function createOrderStatusTrackInfo(data) {
  return request({
    url: '/distribution/order-status-track-info/create',
    method: 'post',
    data: data
  })
}

// 更新配货订单状态跟踪
export function updateOrderStatusTrackInfo(data) {
  return request({
    url: '/distribution/order-status-track-info/update',
    method: 'put',
    data: data
  })
}

// 删除配货订单状态跟踪
export function deleteOrderStatusTrackInfo(id) {
  return request({
    url: '/distribution/order-status-track-info/delete?id=' + id,
    method: 'delete'
  })
}

// 获得配货订单状态跟踪
export function getOrderStatusTrackInfo(id) {
  return request({
    url: '/distribution/order-status-track-info/get?id=' + id,
    method: 'get'
  })
}

// 获得配货订单状态跟踪分页
export function getOrderStatusTrackInfoPage(query) {
  return request({
    url: '/distribution/order-status-track-info/page',
    method: 'get',
    params: query
  })
}

// 导出配货订单状态跟踪 Excel
export function exportOrderStatusTrackInfoExcel(query) {
  return request({
    url: '/distribution/order-status-track-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
