import upload from '@/utils/upload'
import request from '@/utils/request'

// 通过订单编码或货物编码查询订单详情
export function getOrderDetailInfoByCode(code) {
  return request({
    url: '/distribution/order-detail-info/getByCode?code=' + code,
    method: 'GET'
  })
}

// 更新配货订单明细状态
export function updateOrderStatus(data) {
  return request({
    url: '/distribution/order-detail-info/updateOrderStatus',
    method: 'put',
    data: data
  })
}

// 获取最近一个交易日供货商交易综合信息
export function getTodaySupplierList(code) {
  return request({
    url: '/distribution/upstream-order-detail-info/selectTodaySupplierList',
    method: 'GET'
  })
}

// 获取最近一个交易日供货商交易详细信息
export function getTodaySupplierOrderList(data) {
  return request({
    url: '/distribution/upstream-order-detail-info/selectTodaySupplierOrderList',
    method: 'GET',
	data: data
  })
}

// 更新配货订单明细状态至已入库
export function updateOrderStatusToPutStorage(data) {
  return request({
    url: '/distribution/order-detail-info/updateOrderStatusToPutStorage',
    method: 'PUT',
    data: data
  })
}

// 更新配货订单明细状态至未到货
export function updateOrderStatusToNotStock(data) {
  return request({
    url: '/distribution/order-detail-info/updateOrderStatusToNotStock',
    method: 'PUT',
    data: data
  })
}


// 更新配货订单明细状态至登记
export function updateOrderStatusToRegister(data) {
  return request({
    url: '/distribution/order-detail-info/updateOrderStatusToRegister',
    method: 'PUT',
    data: data
  })
}

// 获取最近一个交易日客户交易综合信息
export function getTodayCustomerList(code) {
  return request({
    url: '/distribution/downstream-order-detail-info/selectTodayCustomerList',
    method: 'GET'
  })
}

// 获取最近一个交易日客户交易详细信息
export function getTodayCustomerOrderList(data) {
  return request({
    url: '/distribution/downstream-order-detail-info/selectTodayCustomerOrderList',
    method: 'GET',
	data: data
  })
}

// 更新剩余所有为登记状态的订单状态至未到货
export function updateOrdersStatusToNotReceived(upstreamAlias) {
  return request({
    url: '/distribution/upstream-order-detail-info/updateOrdersStatusToNotReceived?upstreamAlias=' + upstreamAlias,
    method: 'GET',
  })
}