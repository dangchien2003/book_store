import QuillEditor from '@/components/Manager/QuillEditor'
import { toastError, toastWarning } from '@/utils/toast'
import { Box, FormControl, Grid, MenuItem, Select, TextField, Typography } from '@mui/material'
import { useState } from 'react'


const RenderTitle = ({ label }) => {
  return <Typography variant='span' sx={{ fontSize: '18px' }}>{label}: </Typography>
}

const RenderContent = ({ placeholder, value, onChange, type, label }) => {
  return (<TextField
    id='outlined-search'
    label={label}
    type={type ? type : 'search'}
    value={value}
    placeholder={placeholder}
    onChange={onChange}
    sx={{ flex: 1, margin: '0 8px' }} />)
}

const RenderSelect = ({ value, data, onChange }) => {
  return (
    <FormControl sx={{ flex: 1, margin: '0 8px', minWidth: '100px' }}>
      <Select
        labelId="demo-simple-select-label1"
        id="demo-simple-select1"
        value={value}
        onChange={onChange}
      >
        {data?.map((item, index) => {
          return (<MenuItem key={index} value={item.id}>{item.name}</MenuItem>)
        })}
        <MenuItem value={10}>Ten</MenuItem>
        <MenuItem value={20}>Twenty</MenuItem>
        <MenuItem value={30}>Thirty</MenuItem>
      </Select>
    </FormControl>
  )
}

const status = {
  ON_SALE: {
    label: 'Mở bán',
    color: 'success'
  },
  OUT_OF_SALE: {
    label: 'Dừng bán',
    color: 'error'
  },
  HIDE: {
    label: 'Ẩn khỏi gian hàng',
    color: 'warning'
  }
}

const Edit = ({ data }) => {
  const { info, setInfo } = data
  const [dataEdit, setDataEdit] = useState(info)
  const [loadingData, setLoadingData] = useState(true)
  setTimeout(() => {
    setLoadingData(false)
  }, 1000)

  const quillOnChange = (value) => {
    setDataEdit((pre) => ({
      ...pre,
      descripttion: value
    }))
  }


  const handleOnChangeDataEdit = (field, value) => {
    if (loadingData) {
      toastWarning('Đang tải dữ liệu')
      return
    }

    const fieldParts = field.split('.')
    setDataEdit((pre) => {
      const newData = { ...pre }

      fieldParts.reduce((obj, key, index) => {
        if (index === fieldParts.length - 1) {
          obj[key] = value
        } else {
          obj[key] = obj[key] || {}
        }
        return obj[key]
      }, newData)

      return newData
    })
  }


  const handleChangeAuthor = (e) => {
    handleOnChangeDataEdit('authorId', e.target.value)
  }

  const handleChangePublisher = (e) => {
    handleOnChangeDataEdit('publisherId', e.target.value)
  }

  const handleChangeStatus = (e) => {
    handleOnChangeDataEdit('statusCode', e.target.value)
  }

  const handleChangeReprintEdition = (e) => {
    handleOnChangeDataEdit('reprintEdition', e.target.value)
  }
  const handleChangePageSize = (e) => {
    handleOnChangeDataEdit('pageCount', e.target.value)
  }

  const handleChangePrice = (e) => {
    if (e.target.value <= 0) {
      toastWarning('Giá bán phải lớn hơn 0')
      return
    }
    handleOnChangeDataEdit('price', parseInt(e.target.value))
  }

  const handleChangeDiscount = (e) => {
    let value = e.target.value
    if (value > 100 || value < 0) {
      toastWarning('Giá trị chỉ từ 0 đến 100')
      return
    } else if (value === '') {
      value = 0
    }
    handleOnChangeDataEdit('discount', parseInt(value))
  }

  const handleChangeQuantity = () => {
    toastWarning('Không thể chỉnh sửa tại đây')
  }

  const handleChangeSize = (e, method) => {
    let field = 'size.'
    if (method === 'width') {
      field += 'width'
    } else if (method === 'height') {
      field += 'height'
    } else if (method === 'wide') {
      field += 'wide'
    } else {
      toastError('Không thể chỉnh sửa')
    }

    handleOnChangeDataEdit(field, e.target.value)
  }
  return (
    <Box sx={{
      '> *': {
        marginBottom: 5
      }
    }}>
      <Box display='flex'>
        <RenderTitle label='Tên sách' />
        <RenderContent value={dataEdit.name} placeholder={'Nhập tên sách'} onChange={(e) => { handleOnChangeDataEdit('name', e.target.value) }} />
      </Box>
      <Grid container sx={{
        ' > *': {
          margin: {
            lg: 0,
            sm: '8px 0'
          }
        }
      }}>
        <Grid item sm={12} lg={5} display='flex'>
          <RenderTitle label='Tác giả' />
          <FormControl sx={{ flex: 1, margin: '0 8px' }}>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              value={dataEdit.authorId}
              onChange={handleChangeAuthor}
            >
              <MenuItem value={10}>Ten</MenuItem>
              <MenuItem value={20}>Twenty</MenuItem>
              <MenuItem value={30}>Thirty</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item sm={12} lg={7}>
          <RenderTitle label='Nhà xuất bản' />
          <RenderSelect value={dataEdit.publisherId} onChange={handleChangePublisher} />
        </Grid>
      </Grid>
      <Box>
        <RenderTitle label='Tái bản lần thứ' />
        <RenderContent placeholder='Tái bản lần thứ' value={dataEdit.reprintEdition} type='number' onChange={handleChangeReprintEdition} />
      </Box>
      <Box>
        <RenderTitle label='Số lượng' />
        <RenderContent value={dataEdit.availableQuantity} onChange={handleChangeQuantity} />
      </Box>
      <Box>
        <Box sx={{ paddingBottom: '8px' }}>
          <RenderTitle label='Kích thước(mm)' />
        </Box>
        <Grid container sx={{
          ' > *': {
            margin: '8px 0'
          }
        }}>
          <Grid item lg={4} md={12}>
            <RenderContent value={dataEdit.size.width} type='number' onChange={(e) => { handleChangeSize(e, 'width') }} label='Chiều dài' />
          </Grid>
          <Grid item lg={4} md={12}>
            <Typography variant='span'>x</Typography>
            <RenderContent value={dataEdit.size.wide} type='number' onChange={(e) => { handleChangeSize(e, 'wide') }} label='Chiều rộng' />
          </Grid>
          <Grid item lg={4} md={12}>
            <Typography variant='span'>x</Typography>
            <RenderContent value={dataEdit.size.height} type='number' onChange={(e) => { handleChangeSize(e, 'height') }} label='Chiều cao' />
          </Grid >
        </Grid>
      </Box>
      <Grid container>
        <Grid item lg={6} sm={6}>
          <RenderTitle label='Số trang' />
          <RenderContent value={dataEdit.pageCount} type='number' onChange={handleChangePageSize} />
        </Grid>
        <Grid item lg={6} sm={6}>
          <RenderTitle label='Trạng thái' />
          <RenderSelect value={dataEdit.statusCode} data={[]} onChange={handleChangeStatus} />
        </Grid>
      </Grid>

      <Grid container>
        <Grid item lg={4} sm={6}>
          <RenderTitle label='Giá bán(VNĐ)' />
          <RenderContent value={dataEdit.price} type='number' onChange={handleChangePrice} />
        </Grid>
        <Grid item lg={4} sm={6}>
          <RenderTitle label='Giảm giá(%)' />
          <RenderContent value={dataEdit.discount} type='number' onChange={handleChangeDiscount} />
        </Grid>
      </Grid>

      <Box>
        <QuillEditor value={dataEdit.descripttion} readonly={false} onChange={quillOnChange} />
      </Box>
    </Box >
  )
}

export default Edit
