/*
 * Aplikasi Penjualan Token PLN "TokenKu"
 * Tugas Team Based Project Dasar-Dasar Pemrograman (DDP) 2026
 * Kelompok "The Last Star"
 *
 * Catatan:
 * Program ini adalah simulasi yang disederhanakan, tanpa PPJ dan komponen
 * token PLN asli. Daftar token hanya berisi nomor token (tanpa tanggal dan
 * waktu). Seluruh kode ditulis di dalam satu method main (belum memakai OOP).
 */
package tubes_thelaststar_ddp2026;

import java.util.Scanner;

/**
 * Program konsol untuk penjualan token listrik PLN.
 *
 * Alur program:
 * 1. Menu utama menampilkan tiga pilihan (Beli Token, Daftar Token, Keluar).
 * 2. Beli Token meminta data secara berurutan: nomor meter, nama pelanggan,
 *    golongan tarif, nominal token, lalu uang yang dibayarkan.
 * 3. Pengguna dapat mengetik "0" di langkah mana pun (kecuali nama pelanggan)
 *    untuk membatalkan transaksi dan kembali ke menu utama.
 *
 * Singkatan yang dipakai pada nama variabel:
 * jml = jumlah, idx = indeks, maks = maksimal, trx = transaksi, adm = admin.
 *
 * @author The Last Star Tech - Sang Bintang Yang Selalu Datang di Akhir
 */
public class TuBes_TheLastStar_DDP2026 {
    public static void main(String[] args) {
        // Pembaca input keyboard. Semua input dibaca sebagai satu baris penuh.
        Scanner input = new Scanner(System.in);

        // ===== KONFIGURASI TARIF =====

        // Biaya admin tetap yang ditambahkan ke setiap transaksi.
        int biayaAdm = 2500;

        // Tarif per kWh untuk tiap golongan pelanggan.
        // Indeks 0 = Subsidi, indeks 1 = RTM (Rumah Tangga Mampu).
        int[] tarifGolongan = {605, 1352};

        // Nama golongan, urutannya harus sama persis dengan tarifGolongan.
        String[] namaGolongan = {"900 VA Subsidi", "900 VA RTM"};

        // Pilihan nominal token yang tersedia (dalam rupiah).
        // Pilihan menu 1 sampai 4 dipetakan ke indeks 0 sampai 3.
        int[] daftarNominal = {20000, 50000, 100000, 200000};

        // ===== PENYIMPANAN TOKEN =====

        // Daftar token disimpan di array biasa dan hilang saat program ditutup.
        int maksToken = 50;
        String[] daftarToken = new String[maksToken];
        int jmlToken = 0;

        // Kata kunci untuk membatalkan transaksi dan kembali ke menu utama.
        // Memakai angka "0" karena nama pelanggan boleh berisi teks apa pun,
        // termasuk kata "batal".
        String cmdKembali = "0";

        // Penanda perulangan menu utama. Bernilai true saat pengguna memilih Keluar.
        boolean keluar = false;

        // ===== PERULANGAN MENU UTAMA =====
        while (!keluar) {
            System.out.print("""

                    MENU UTAMA
                    1. Beli Token
                    2. Daftar Token
                    0. Keluar

                    Pilih menu:\s""");
            String pilihanMenu = input.nextLine().trim();

            switch (pilihanMenu) {
            case "1" -> {
                System.out.println("\n--- BELI TOKEN ---\n");

                // Penanda pembatalan. Berubah menjadi true jika pengguna mengetik
                // kata kunci kembali di salah satu langkah. Setelah true, semua
                // langkah berikutnya dilewati sehingga program kembali ke menu utama.
                boolean trxBatal = false;

                // ----- Langkah 1: nomor meter -----
                // Disimpan sebagai String agar angka 0 di depan tidak hilang.
                // Format yang diterima adalah tepat 11 karakter dan semuanya angka.
                String noMeter = "";
                boolean meterValid = false;
                while (!trxBatal && !meterValid) {
                    System.out.print("Masukkan nomor meter (11 digit angka, 0 = kembali): ");
                    noMeter = input.nextLine().trim();
                    if (noMeter.equals(cmdKembali)) {
                        trxBatal = true;
                    } else if (noMeter.matches("\\d{11}")) {
                        meterValid = true;
                    } else {
                        System.out.println("Error: nomor meter harus 11 digit angka!\n");
                    }
                }

                // ----- Langkah 2: nama pelanggan -----
                // Tidak boleh kosong. Langkah ini tidak punya kata kunci kembali,
                // jadi apa pun yang diketik dianggap bagian dari nama.
                String namaPelanggan = "";
                while (!trxBatal && namaPelanggan.isEmpty()) {
                    System.out.print("\nMasukkan nama pelanggan: ");
                    namaPelanggan = input.nextLine().trim();
                    if (namaPelanggan.isEmpty()) {
                        System.out.println("Nama tidak boleh kosong.");
                    }
                }

                // ----- Langkah 3: golongan tarif -----
                // Menyimpan indeks golongan (0 = Subsidi, 1 = RTM).
                // Nilai -1 berarti golongan belum dipilih.
                int idxGolongan = -1;
                while (!trxBatal && idxGolongan < 0) {
                    System.out.print("""

                            Pilih golongan tarif:
                              1. 900 VA (Subsidi)
                              2. 900 VA RTM

                            Pilihan (1/2, 0 = kembali):\s""");
                    String inputGolongan = input.nextLine().trim();
                    if (inputGolongan.equals(cmdKembali)) {
                        trxBatal = true;
                    } else if (inputGolongan.equals("1")) {
                        idxGolongan = 0;
                    } else if (inputGolongan.equals("2")) {
                        idxGolongan = 1;
                    } else {
                        System.out.println("Golongan tidak valid.");
                    }
                }

                // ----- Langkah 4: nominal token -----
                // Menyimpan indeks nominal (0 sampai 3).
                // Nilai -1 berarti nominal belum dipilih.
                int idxNominal = -1;
                while (!trxBatal && idxNominal < 0) {
                    System.out.print("""

                            Pilih nominal token:
                              1. 20000
                              2. 50000
                              3. 100000
                              4. 200000

                            Pilihan (1-4, 0 = kembali):\s""");
                    String inputNominal = input.nextLine().trim();
                    if (inputNominal.equals(cmdKembali)) {
                        trxBatal = true;
                        // Input harus persis 1 digit diantara angka 1-4, selain itu otomatis masuk kondisi 'else'
                    } else if (inputNominal.matches("[1-4]")) {
                        // Pilihan menu dimulai dari 1, sedangkan indeks array dari 0.
                        idxNominal = Integer.parseInt(inputNominal) - 1;
                    } else {
                        System.out.println("Nominal tidak valid.");
                    }
                }

                // ----- Langkah 5 dan seterusnya: hanya jalan jika belum dibatalkan -----
                // Pengecekan ini juga mencegah error indeks array (misalnya
                // tarifGolongan[-1]) ketika pengguna membatalkan lebih awal.
                if (!trxBatal) {
                    // Ambil data sesuai pilihan pengguna.
                    int tarifKwh = tarifGolongan[idxGolongan];
                    int nominal = daftarNominal[idxNominal];

                    // Total bayar adalah nominal ditambah biaya admin.
                    int totalBayar = nominal + biayaAdm;

                    // Jumlah kWh adalah nominal dibagi tarif per kWh.
                    // Hasilnya diformat 2 angka desimal, lalu titik diganti koma.
                    String kwhTeks = String.format("%.2f", (double) nominal / tarifKwh)
                            .replace(".", ",");

                    System.out.printf("""

                            RINCIAN PEMBAYARAN
                            Golongan    : %s
                            Tarif       : %d/kWh
                            Nominal     : %d
                            Admin       : %d
                            Total Bayar : %d
                            """, namaGolongan[idxGolongan], tarifKwh, nominal,
                            biayaAdm, totalBayar);

                    // ----- Langkah pembayaran -----
                    // Diulang sampai uang yang dibayar cukup, atau pengguna membatalkan.
                    // Input harus berupa angka 1 sampai 9 digit agar tidak melebihi
                    // batas tipe int.
                    int uangBayar = 0;
                    while (!trxBatal && uangBayar < totalBayar) {
                        System.out.print("\nMasukkan uang dibayar (0 = kembali): ");
                        String inputUang = input.nextLine().trim();
                        if (inputUang.equals(cmdKembali)) {
                            trxBatal = true;
                            // d{1,9} adalah pembatas agar input hanya berupa angka 0-9 dengan panjang digit 1 sampai 9
                            // kenapa 9 digit? karena int punya batas max sekitar 2,1 milyar, kalau kita masukkan angka lebih dari itu,
                            // program akan error. 9 digit angka terbesar adalah 999.999.999, tidak akan error.
                        } else if (!inputUang.matches("\\d{1,9}")) {
                            System.out.println("Uang harus berupa angka.");
                        } else {
                            uangBayar = Integer.parseInt(inputUang);
                            if (uangBayar < totalBayar) {
                                System.out.println("Uang kurang! Total bayar " + totalBayar + ".");
                            }
                        }
                    }

                    // ----- Struk dan token: hanya jika pembayaran selesai -----
                    if (!trxBatal) {
                        int kembalian = uangBayar - totalBayar;

                        // Buat token acak 20 digit dengan format xxxx-xxxx-xxxx-xxxx-xxxx.
                        // Test : XXXX-XXXX-XXXX-XXXX
                        // Setiap digit berasal dari Math.random() dikali 10 lalu dibuang
                        // desimalnya, sehingga hasilnya 0 sampai 9. Tanda "-" disisipkan
                        // setelah digit ke-4, 8, 12, dan 16.
                        String token = "";
                        for (int i = 1; i <= 20; i++) {
                            token += (int) (Math.random() * 10);
                            if (i % 4 == 0 && i < 20) {
                                token += "-";
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
                                """, namaPelanggan, noMeter, namaGolongan[idxGolongan],
                                tarifKwh, nominal, biayaAdm, totalBayar, uangBayar,
                                kembalian, kwhTeks, token);

                        // Simpan token ke daftar jika masih ada tempat.
                        if (jmlToken < maksToken) {
                            daftarToken[jmlToken] = token;
                            jmlToken++;
                        } else {
                            System.out.println("Daftar token penuh, token tidak disimpan.");
                        }
                    }
                }

                // Pesan ini dicetak sekali saja jika transaksi dibatalkan di langkah mana pun.
                // Program kembali ke menu utama karena case "1" selesai dan perulangan
                // while (!keluar) mengulang dari awal.
                if (trxBatal) {
                    System.out.println("\nKembali ke menu utama.");
                }
            }
            case "2" -> {
                // Tampilkan semua token yang sudah tersimpan.
                System.out.println("\n======= DAFTAR TOKEN =======");
                if (jmlToken == 0) {
                    System.out.println("Belum ada token.");
                }
                for (int idxToken = 0; idxToken < jmlToken; idxToken++) {
                    System.out.println((idxToken + 1) + ". " + daftarToken[idxToken]);
                }
                System.out.println("============================");
            }
            case "0" -> {
                // Hentikan perulangan menu utama.
                keluar = true;
                System.out.println("\nTerima kasih telah menggunakan TokenKu!");
            }
            default -> {
                // Pilihan menu bukan 0, 1, atau 2.
                System.out.println("\nPilihan tidak valid. Silakan coba lagi.");
            }
            }
        }
    }
}