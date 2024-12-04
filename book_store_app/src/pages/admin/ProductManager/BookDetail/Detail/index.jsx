import { formatCurrency } from '@/utils/format'
import { Delete, Edit, Save } from '@mui/icons-material'
import { Box, Button, Chip, Grid, TextField, Typography } from '@mui/material'
import { useState } from 'react'

const RenderTitle = ({ label }) => {
  return <Typography variant='span' sx={{ fontSize: '18px' }}>{label}: </Typography>
}

const RenderContent = ({ label, strikethrough }) => {
  return <Typography variant='span'
    sx={{
      margin: '0 10px', fontSize: '20px', borderBottom: '2px solid #bfccd0', padding: '0 5px',
      textDecoration: strikethrough ? 'line-through' : 'none'
    }}>{label}</Typography>
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

const GenStatus = ({ id }) => {
  const data = status[id] ? status[id] : { label: id, color: 'warning' }
  return (<Chip label={data.label} color={data.color} sx={{ fontSize: '16px', margin: '10px' }} />)
}

const infoFake = {
  name: 'Sách dạy code c Sách dạy code c',
  author: 'Lê Đăng Chiến',
  publisher: 'Kim Đồng',
  reprintEdition: 1,
  availableQuantity: 20,
  size: {
    width: 120,
    height: 20,
    wide: 60
  },
  pageCount: 200,
  statusCode: 'ON_SALE',
  price: 200000,
  discount: 10,
  descripttion: ''
}
const Detail = () => {

  const [editing, setEditing] = useState(false)
  const [info, setInfo] = useState(infoFake)
  const [infoEdit, setInfoEdit] = useState(infoFake)

  const handleToggleEdit = () => {
    setEditing(pre => !pre)
  }

  return (
    <Box sx={{ background: 'white', padding: '20px', borderRadius: '15px' }}>
      <Box sx={{
        paddingBottom: '15px',
        textAlign: 'right',
        ' > *': {
          margin: '0 8px',
          minWidth: '100px'
        }
      }}>
        {!editing
          ? (<Button variant="contained" endIcon={<Edit />} onClick={handleToggleEdit}>
            Chỉnh sửa
          </Button>)
          : (<>
            <Button variant="contained" sx={{ background: '#70f03e' }} endIcon={<Save />}>
              Lưu
            </Button>
            <Button variant="contained" sx={{ background: '#eb4d38' }} endIcon={<Delete />} onClick={handleToggleEdit}>
              Huỷ
            </Button>
          </>)}

      </Box>
      <Box sx={{
        '> *': {
          marginBottom: 5
        }
      }}>
        <Box>
          <RenderTitle label='Tên sách'></RenderTitle>
          <RenderContent label={info.name} />
        </Box>
        <Grid container sx={{
          ' > *': {
            marginTop: {
              sm: 2,
              lg: 0
            }
          }
        }}>
          <Grid item sm={12} lg={5} >
            <RenderTitle label='Tác giả' />
            <RenderContent label={info.author} />
          </Grid>
          <Grid item sm={12} lg={7} >
            <RenderTitle label='Nhà xuất bản' />
            <RenderContent label={`Nhà xuất bản ${info.publisher}`} />
          </Grid>
        </Grid>
        <Box>
          <RenderTitle label='Tái bản' />
          <RenderContent label={`Lần thứ ${info.reprintEdition}`} />
        </Box>
        <Box>
          <RenderTitle label='Số lượng' />
          <RenderContent label={info.availableQuantity} />
        </Box>
        <Box>
          <RenderTitle label='Kích thước' />
          <RenderContent label={info.size.width} />
          <Typography variant='span'>x</Typography>
          <RenderContent label={info.size.wide} />
          <Typography variant='span'>x</Typography>
          <RenderContent label={info.size.height} />
        </Box>
        <Grid container>
          <Grid item lg={4} sm={6}>
            <RenderTitle label='Số trang' />
            <RenderContent label={info.pageCount} />
          </Grid>
          <Grid item lg={8} sm={6}>
            <RenderTitle label='Trạng thái' />
            <GenStatus id={info.statusCode} />
          </Grid>
        </Grid>

        <Grid container>
          <Grid item lg={4} sm={6}>
            <RenderTitle label='Giá bán' />
            <Box display='inline-block' sx={{ position: 'relative' }}>
              <RenderContent label={formatCurrency(info.price)} strikethrough />
              <Typography sx={{ position: 'absolute', left: '15px' }}>{formatCurrency(info.price - (info.price * info.discount / 100))}</Typography>
            </Box>
          </Grid>
          <Grid item lg={4} sm={6}>
            <RenderTitle label='Giảm giá' />
            <RenderContent label={`${info.discount}%`} />
          </Grid>
        </Grid>

        <Box>
          <TextField
            label="Mô tả sản phẩm"
            multiline
            minRows={4}
            maxRows={10}
            placeholder="Mô tả tại đây..."
            fullWidth
            variant="outlined"
            InputProps={{
              readOnly: true
            }}
          />
        </Box>
      </Box >
    </Box>
  )
}

export default Detail
