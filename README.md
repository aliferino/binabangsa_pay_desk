# binabangsa_pay_desk

Aplikasi desktop Java untuk sistem pembayaran SPP LSP (Bina Bangsa).

## Perbedaan dengan versi web

Aplikasi desktop ini berbeda dari versi web:

- Metode bayar: TUNAI dan QRIS (bukan Virtual Account).
- Teller mencatat pembayaran langsung (wajib ada).
- Tidak ada login siswa.
- Tidak ada payment gateway atau callback webhook.

Database tetap satu, yaitu `binabangsa_pay_web` yang sudah ada. Aplikasi
desktop hanya membaca dan menambah baris, tidak mengubah atau menghapus
data yang sudah ada.

## Prasyarat

- Java 24 atau lebih baru (JAVA_HOME sudah diatur).
- MySQL 8.0 (lewat Laragon), database `binabangsa_pay_web` sudah ada.
- Driver MySQL Connector/J 9.0.0 di folder `lib/`.

## Cara kompilasi

```bash
cd D:/applications/binabangsa_pay_desk
export JAVA_HOME="C:/AndroidStudio/jbr"
mkdir -p build/out
javac -encoding UTF-8 -cp "lib/*" -d build/out src/*.java
```

Harus keluar tanpa galat.

## Cara menjalankan

```bash
java -cp "build/out;lib/*" Main
```

Akan tampil jendela utama dengan judul "Sistem Pembayaran SPP".

## Cara mengganti alamat database

Edit berkas `config.properties` di root project:

```properties
db.host=localhost
db.port=3306
db.name=binabangsa_pay_web
db.user=root
db.password=
```

Tulis alamat host, port, nama database, user, dan sandi yang sesuai.
Jangan taruh kata sandi asli di sini kalau repo ini publik.

## Struktur folder

```
binabangsa_pay_desk/
  src/          kode Java
  lib/          pustaka (driver MySQL)
  nbproject/    berkas proyek NetBeans Ant
  build/        hasil kompilasi (diabaikan git)
  dist/         hasil packaging (diabaikan git)
  config.properties
  build.xml
  README.md
```

## Membuka di NetBeans

1. File > Open Project.
2. Pilih folder `binabangsa_pay_desk`.
3. Klik kanan project, Run.

NetBeans akan memakai berkas `build.xml` dan `nbproject/` untuk
kompilasi dan menjalankan.
