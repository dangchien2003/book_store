import { Delete, Edit as EditIcon, Save } from '@mui/icons-material'
import { Box, Button } from '@mui/material'
import { useState } from 'react'
import Show from './Show'
import Edit from './Edit'

const infoFake = {
  name: 'Sách dạy code c Sách dạy code c',
  author: 'Lê Đăng Chiến',
  authorId: 30,
  publisher: 'Kim Đồng',
  publisherId: 10,
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
  descripttion: '<h1>CHiến</h1>'
}

const Detail = () => {

  const [editing, setEditing] = useState(false)
  const [info, setInfo] = useState(infoFake)

  const handleToggleEdit = () => {
    setEditing(pre => !pre)
  }

  return (
    <Box sx={{ background: 'white', padding: '20px', borderRadius: '15px' }}>
      <Box sx={{
        paddingBottom: '15px',
        textAlign: 'right',
        ' > button': {
          margin: '0 8px',
          minWidth: '100px'
        }
      }}>
        {!editing
          ? (<Button variant="contained" endIcon={<EditIcon />} onClick={handleToggleEdit}>
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
      {!editing ? <Show info={info} /> : <Edit data={{ info, setInfo }} />}
    </Box>
  )
}

export default Detail
