import subprocess
import time
import os

adb_serial = '192.168.8.11:43517'
out_dir = r'C:\Users\Administrator\Desktop\My Produk\App Android\Livin\'by Mandiri\audit_tests'.replace("'", "")
# Let's use clean path
out_dir = 'C:/Users/Administrator/Desktop/My Produk/App Android/Livin\'by Mandiri/audit_tests'
os.makedirs(out_dir, exist_ok=True)

def adb_cmd(cmd):
    full = f'adb -s {adb_serial} {cmd}'
    res = subprocess.run(full, shell=True, capture_output=True, text=True)
    return res.stdout

def tap(x, y, wait=1.0):
    adb_cmd(f'shell input tap {x} {y}')
    time.sleep(wait)

def dismiss():
    adb_cmd('shell input keyevent KEYCODE_BACK')
    time.sleep(0.8)

def capture(name):
    adb_cmd(f'shell screencap -p /sdcard/{name}.png')
    adb_cmd(f'pull /sdcard/{name}.png "{out_dir}/{name}.png"')
    print(f'Captured {name}.png')

print('Starting comprehensive button-by-button test...')
subprocess.run(f'adb connect {adb_serial}', shell=True)
time.sleep(1)

# Wake up and unlock if needed
adb_cmd('shell input keyevent KEYCODE_WAKEUP')
adb_cmd('shell input keyevent 82')
time.sleep(0.5)

# Bring app to front
adb_cmd('shell am start -n com.mandiri.livin/.MainActivity')
time.sleep(2.5)

# Test 1: Livin Call
print('Test 1: Livin Call button (Top-Right)')
tap(610, 100)
capture('t01_livin_call')
dismiss()

# Test 2: Security Pill
print('Test 2: Security Announcement Pill')
tap(360, 850)
capture('t02_security_tips')
dismiss()

# Test 3: e-money
print('Test 3: e-money icon')
tap(90, 1030)
capture('t03_emoney')
dismiss()

# Test 4: Quick Pick
print('Test 4: Quick Pick icon')
tap(270, 1030)
capture('t04_quick_pick')
dismiss()

# Test 5: QR Bayar
print('Test 5: QR Bayar icon')
tap(450, 1030)
capture('t05_qr_bayar')
dismiss()

# Test 6: QRIS Tap
print('Test 6: QRIS Tap icon')
tap(630, 1030)
capture('t06_qris_tap')
dismiss()

# Test 7: Arrow Next -> Page 2
print('Test 7: Carousel Next Arrow Button')
tap(415, 1270)
capture('t07_page2_by_arrow')

# Test 8: QR Terima Transfer
print('Test 8: QR Terima Transfer icon (Page 2)')
tap(270, 1030)
capture('t08_qr_terima')
dismiss()

# Test 9: Tap to Pay
print('Test 9: Tap to Pay icon (Page 2)')
tap(450, 1030)
capture('t09_tap_to_pay')
dismiss()

# Test 10: Setor Tarik
print('Test 10: Setor Tarik icon (Page 2)')
tap(630, 1030)
capture('t10_setor_tarik')
dismiss()

# Test 11: Arrow Prev <- Page 1
print('Test 11: Carousel Prev Arrow Button')
tap(305, 1270)
capture('t11_page1_by_arrow')

# Test 12: Login Biometric
print('Test 12: Primary Login Biometric Button')
tap(360, 1350)
capture('t12_login_biometric')
dismiss()

# Test 13: Intip Saldo Peek
print('Test 13: Geser untuk Intip Saldo Header')
tap(360, 80, wait=0.6)
capture('t13_intip_saldo_peek')

print('All 13 button tests completed successfully!')
