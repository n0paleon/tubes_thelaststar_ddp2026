/*
 * aplikasi Penjualan Token PLN "TokenKu"
 * tugas Team Based Project Dasar-Dasar Pemrograman (DDP) 2026 kelompok "The Last Star"
 *
 * catatan: ini simulasi yang disederhanakan, tanpa PPJ dan komponen
 * token PLN asli. daftar token hanya berisi nomor token (tanpa tanggal
 * dan waktu) karena library yang diizinkan masih sangat sederhana.
 * seluruh kode ditulis di dalam satu method main (belum memakai OOP).
 */
package tubes_thelaststar_ddp2026;

import java.util.Scanner;

/**
 * program konsol penjualan token listrik PLN.
 * @author The Last Star - Sang Bintang Yang Selalu Datang di Akhir
 */
public class TuBes_TheLastStar_DDP2026 {

    public static void main(String[] args) {
        // scanner utama untuk membaca input keyboard.
        // setiap input dibaca sebagai satu baris penuh dengan nextLine()
        Scanner input = new Scanner(System.in);

        // konfigurasi biaya admin dan tarif per kWh berdasarkan 2 jenis pelanggan (Subsidi & RTM - Rumah Tangga Mampu)
        int admin = 2500;
        int tarifSubsidi = 605;
        int tarifRtm = 1352;

        // daftar token memakai array biasa.
        // isinya hanya nomor token dan hilang saat program ditutup
        int maksToken = 50;
        String[] daftarToken = new String[maksToken];
        int jumlahToken = 0;

        // kata kunci angka yang bisa diketik di langkah mana pun saat Beli Token
        // untuk membatalkan transaksi dan kembali ke menu utama.
        // memakai angka "0" karena nama pelanggan boleh berisi teks apa pun,
        // termasuk "batal"
        String kembali = "0";

        boolean jalan = true;
        // label pada loop utama supaya setiap langkah Beli Token bisa
        // langsung kembali ke menu dengan "continue menuUtama"
        menuUtama: while (jalan) {
            // menu utama
            System.out.println();
            System.out.printf("""
                    MENU UTAMA
                    1. Beli Token
                    2. Daftar Token
                    0. Keluar
                              
                    Pilih menu:\s""");

            // baris input dibaca dulu, lalu dicek apakah berupa angka.
            // jika bukan angka, pilihan tetap -1 dan jatuh ke default
            String baris = input.nextLine();
            int pilihan = -1;
            Scanner scannerMenu = new Scanner(baris);
            if (scannerMenu.hasNextInt()) {
                pilihan = scannerMenu.nextInt();
            }

            switch (pilihan) {
            case 1 -> {
                System.out.println("\n--- BELI TOKEN ---");
                System.out.println();

                // nomor meter disimpan sebagai String agar angka 0 di depan
                // tidak hilang. validasi dilakukan per karakter supaya input
                // seperti "12345 67890" atau "+1234567890" tidak lolos
                String noMeter = "";
                while (true) {
                    System.out.print("Masukkan nomor meter (11 digit angka, 0 = kembali): ");
                    noMeter = input.nextLine().trim();
                    if (noMeter.equals(kembali)) {
                        System.out.println("\nKembali ke menu utama.");
                        continue menuUtama;
                    }

                    boolean valid = noMeter.length() == 11;
                    for (int i = 0; i < noMeter.length(); i++) {
                        if (!Character.isDigit(noMeter.charAt(i))) {
                            valid = false;
                        }
                    }
                    if (valid) {
                        break;
                    }
                    System.out.println("Error: nomor meter harus 11 digit angka dan tidak boleh mengandung huruf!");
                    System.out.println();
                }

                // nama pelanggan tidak boleh kosong.
                // di langkah ini tidak ada kata kunci kembali: apa pun yang
                // diketik (termasuk "batal") dianggap bagian dari nama
                String nama = "";
                while (nama.isEmpty()) {
                    System.out.println();
                    System.out.print("Masukkan nama pelanggan: ");
                    nama = input.nextLine().trim();
                    if (nama.isEmpty()) {
                        System.out.println("Nama tidak boleh kosong. Silakan ulang.");
                    }
                }

                // pilih golongan tarif, hanya menerima angka 1 atau 2
                int golongan = 0;
                while (golongan != 1 && golongan != 2) {
                    System.out.println();
                    System.out.printf("""
                            Pilih golongan tarif:
                              1. 900 VA (Subsidi)
                              2. 900 VA RTM
                                      
                            Pilihan (1/2, 0 = kembali):\s""");
                    String barisGolongan = input.nextLine().trim();
                    if (barisGolongan.equals(kembali)) {
                        System.out.println("\nKembali ke menu utama.");
                        continue menuUtama;
                    }
                    Scanner scannerGolongan = new Scanner(barisGolongan);
                    if (scannerGolongan.hasNextInt()) {
                        golongan = scannerGolongan.nextInt();
                    } else {
                        golongan = 0;
                    }
                    if (golongan != 1 && golongan != 2) {
                        System.out.println("Golongan tidak valid. Silakan ulang.");
                    }
                }

                // tentukan nama golongan dan tarif per kWh sesuai pilihan
                String namaGolongan;
                int tarif;
                if (golongan == 1) {
                    namaGolongan = "900 VA Subsidi";
                    tarif = tarifSubsidi;
                } else {
                    namaGolongan = "900 VA RTM";
                    tarif = tarifRtm;
                }

                // pilih nominal token, diulang sampai pilihan 1 sampai 4 valid
                int nominal = 0;
                while (nominal == 0) {
                    System.out.println();
                    System.out.printf("""
                            Pilih nominal token:
                              1. 20000
                              2. 50000
                              3. 100000
                              4. 200000
                                      
                            Pilihan (1-4, 0 = kembali):\s""");
                    String barisNominal = input.nextLine().trim();
                    if (barisNominal.equals(kembali)) {
                        System.out.println("\nKembali ke menu utama.");
                        continue menuUtama;
                    }
                    Scanner scannerNominal = new Scanner(barisNominal);
                    int pilihanNominal = 0;
                    if (scannerNominal.hasNextInt()) {
                        pilihanNominal = scannerNominal.nextInt();
                    }

                    switch (pilihanNominal) {
                    case 1 -> nominal = 20000;
                    case 2 -> nominal = 50000;
                    case 3 -> nominal = 100000;
                    case 4 -> nominal = 200000;
                    default -> System.out.println("Nominal tidak valid. Silakan ulang.");
                    }
                }

                // total bayar adalah nominal ditambah biaya admin.
                // jumlah kWh adalah nominal dibagi tarif per kWh
                int totalBayar = nominal + admin;
                double jumlahKWh = (double) nominal / tarif;

                // format kWh menjadi 2 angka desimal. titik diganti koma
                // agar hasilnya sama di semua pengaturan locale
                String kWhTeks = String.format("%.2f", jumlahKWh).replace(".", ",");

                System.out.printf("""

                        RINCIAN PEMBAYARAN
                        Golongan    : %s
                        Tarif       : %d/kWh
                        Nominal     : %d
                        Admin       : %d
                        Total Bayar : %d
                        """, namaGolongan, tarif, nominal, admin, totalBayar);

                // input uang diulang jika bukan angka atau kurang dari total bayar
                int uangDibayar = 0;
                while (uangDibayar < totalBayar) {
                    System.out.println();
                    System.out.print("Masukkan uang dibayar (0 = kembali): ");
                    String barisUang = input.nextLine().trim();
                    if (barisUang.equals(kembali)) {
                        System.out.println("\nKembali ke menu utama.");
                        continue menuUtama;
                    }
                    Scanner scannerUang = new Scanner(barisUang);
                    if (scannerUang.hasNextInt()) {
                        uangDibayar = scannerUang.nextInt();
                    } else {
                        uangDibayar = 0;
                        System.out.println("Uang harus berupa angka. Silakan ulang.");
                        continue;
                    }
                    if (uangDibayar < totalBayar) {
                        System.out.printf("""
                                Uang kurang! Total bayar %d. Silakan ulang.
                                """, totalBayar);
                    }
                }

                // hitung kembalian lalu buat token acak 20 digit.
                // setiap digit dibuat dari Math.random() dikali 10 dan dibuang
                // desimalnya sehingga hasilnya 0 sampai 9.
                // tanda "-" disisipkan setelah digit ke-4, 8, 12, dan 16
                int kembalian = uangDibayar - totalBayar;
                String token = "";
                for (int i = 1; i <= 20; i++) {
                    int digit = (int) (Math.random() * 10);
                    token = token + digit;
                    if (i % 4 == 0 && i < 20) {
                        token = token + "-";
                    }
                }

                System.out.printf("""

                        ========== STRUK TOKENKU ==========
                        Nama        : %s
                        No. Meter   : %s
                        Golongan    : %s
                        Tarif       : %d/kWh
                        Nominal     : %d
                        Admin       : %d
                        Total Bayar : %d
                        Tunai       : %d
                        Kembalian   : %d
                        kWh         : %s
                        Token       : %s
                        ===================================
                        """, nama, noMeter, namaGolongan, tarif, nominal, admin,
                        totalBayar, uangDibayar, kembalian, kWhTeks, token);

                // simpan token ke daftar jika masih ada tempat
                if (jumlahToken < maksToken) {
                    daftarToken[jumlahToken] = token;
                    jumlahToken++;
                } else {
                    System.out.println("Daftar token penuh, token tidak disimpan.");
                }

            }
            case 2 -> {
                // tampilkan semua token yang sudah tersimpan
                System.out.printf("""

                        ======= DAFTAR TOKEN =======
                        """);
                if (jumlahToken == 0) {
                    System.out.println("Belum ada token.");
                    System.out.println();
                } else {
                    for (int i = 0; i < jumlahToken; i++) {
                        System.out.printf("""
                                %d. %s
                                """, i + 1, daftarToken[i]);
                        System.out.println();
                    }
                }
                System.out.printf("""
                        ============================
                        """);

            }
            case 0 -> {
                // hentikan perulangan menu utama
                jalan = false;
                System.out.println();
                System.out.println("Terima kasih telah menggunakan TokenKu!");
            }
            default -> {
                // pilihan menu bukan 0, 1, atau 2
                System.out.println();
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
            }
        }
    }
}
