// ── DATA CLASS ──
data class Karyawan(
    val id: Int,                  
    var nama: String,             
    var jabatan: String,          
    var gaji: Double,             
    var noHp: String?            
)

// ── CLASS dengan Custom GETTER & SETTER
class DataKaryawan(karyawan: Karyawan) {

    private var _karyawan: Karyawan = karyawan

    // Otomatis Kapital
    val nama: String
        get() = _karyawan.nama
            .split(" ")
            .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

    // Format gaji "Rp"
    val gaji: String
        get() = "Rp ${"%,.0f".format(_karyawan.gaji)}"

    // Gaji tidak boleh negatif
    fun setGaji(nilaiGaji: Double) {
        if (nilaiGaji < 0) {
            println("  Gaji tidak boleh negatif! Perubahan dibatalkan.")
        } else {
            _karyawan.gaji = nilaiGaji
            println("  Gaji berhasil diperbarui.")
        }
    }

    // Nama tidak boleh kosong
    fun setNama(namaBaru: String) {
        if (namaBaru.isBlank()) {
            println("  Nama tidak boleh kosong! Perubahan dibatalkan.")
        } else {
            _karyawan.nama = namaBaru
            println("  Nama berhasil diperbarui.")
        }
    }

    // null safety 
    val noHp: String
        get() = _karyawan.noHp ?: "Belum diisi"   // Elvis operator

    // Getter akses data
    fun getKaryawan(): Karyawan = _karyawan
}

// ── STORAGE ──
val daftarKaryawan: ArrayList<Karyawan> = ArrayList()
var nextId: Int = 1  // auto increment ID

// ── HELPER ──
fun garis() = println("=".repeat(50))
fun garisTipis() = println("-".repeat(50))


// ── MENU 1 : TAMBAH DATA ──
fun tambahData() {
    garis()
    println("  TAMBAH DATA KARYAWAN")
    garis()

    print("  Nama       : ")
    val inputNama = readLine() ?: ""

    // Null safety
    if (inputNama.isBlank()) {
        println("  Nama tidak boleh kosong! Data tidak disimpan.")
        return
    }

    print("  Jabatan    : ")
    val inputJabatan = readLine() ?: "Tidak diketahui"

    print("  Gaji       : ")
    val inputGaji = readLine()?.toDoubleOrNull()  // Nullable
    if (inputGaji == null || inputGaji < 0) {
        println("  Input gaji tidak valid! Data tidak disimpan.")
        return
    }

    print("  No HP (kosongkan jika belum ada): ")
    val inputNoHp: String? = readLine()?.takeIf { it.isNotBlank() }  // Nullable

    val karyawanBaru = Karyawan(
        id      = nextId++,
        nama    = inputNama,
        jabatan = inputJabatan,
        gaji    = inputGaji,
        noHp    = inputNoHp    // Nullable
    )

    daftarKaryawan.add(karyawanBaru)
    println("\n  Karyawan '${karyawanBaru.nama}' berhasil ditambahkan! (ID: ${karyawanBaru.id})")
}

// ── MENU 2 : LIST DATA ──
fun listData() {
    garis()
    println("  DAFTAR SELURUH KARYAWAN")
    garis()

    if (daftarKaryawan.isEmpty()) {
        println("  (Belum ada data karyawan)")
        return
    }

    // Iterasi ArrayList
    daftarKaryawan.forEachIndexed { index, karyawan ->
        val data = DataKaryawan(karyawan)   // pakai class wrapper untuk getter custom
        println("  ${index + 1}. [ID: ${karyawan.id}] ${data.nama}")
        println("     Jabatan : ${karyawan.jabatan}")
        println("     Gaji    : ${data.gaji}")
        println("     No HP   : ${data.noHp}")   
        if (index < daftarKaryawan.size - 1) garisTipis()
    }
}

// ── MENU 3 : EDIT DATA ──
fun editData() {
    garis()
    println("  EDIT DATA KARYAWAN")
    garis()

    if (daftarKaryawan.isEmpty()) {
        println("  (Belum ada data karyawan)")
        return
    }

    listData()
    print("\n  Masukkan ID karyawan yang ingin diedit: ")
    val inputId = readLine()?.toIntOrNull()

    // Null safety — toIntOrNull() bisa return null
    if (inputId == null) {
        println("  Input tidak valid!")
        return
    }

    val karyawan = daftarKaryawan.find { it.id == inputId }
    if (karyawan == null) {
        println("  Karyawan dengan ID $inputId tidak ditemukan!")
        return
    }

    val data = DataKaryawan(karyawan)  // gunakan class wrapper untuk setter custom

    println("\n  Data saat ini: ${data.nama} | ${karyawan.jabatan} | ${data.gaji} | No HP: ${data.noHp}")
    garisTipis()
    println("  Kosongkan (tekan Enter) untuk tidak mengubah field tersebut")
    garisTipis()

    print("  Nama baru       : ")
    val namaBaru = readLine()
    if (!namaBaru.isNullOrBlank()) data.setNama(namaBaru)  

    print("  Jabatan baru    : ")
    val jabatanBaru = readLine()
    if (!jabatanBaru.isNullOrBlank()) karyawan.jabatan = jabatanBaru

    print("  Gaji baru       : ")
    val gajiBaru = readLine()?.toDoubleOrNull()
    if (gajiBaru != null) data.setGaji(gajiBaru)   

    print("  No HP baru (ketik 'hapus' untuk mengosongkan): ")
    val noHpBaru = readLine()
    when {
        noHpBaru.equals("hapus", ignoreCase = true) -> karyawan.noHp = null   
        !noHpBaru.isNullOrBlank()                   -> karyawan.noHp = noHpBaru
    }

    println("\n  Data karyawan '${data.nama}' berhasil diperbarui!")
}

// ── MENU 4 : HAPUS DATA ──
fun hapusData() {
    garis()
    println("  HAPUS DATA KARYAWAN")
    garis()

    if (daftarKaryawan.isEmpty()) {
        println("  (Belum ada data karyawan)")
        return
    }

    listData()
    print("\n  Masukkan ID karyawan yang ingin dihapus: ")
    val inputId = readLine()?.toIntOrNull()

    if (inputId == null) {
        println("  Input tidak valid!")
        return
    }

    val karyawan = daftarKaryawan.find { it.id == inputId }
    if (karyawan == null) {
        println("  Karyawan dengan ID $inputId tidak ditemukan!")
        return
    }

    print("  Yakin hapus '${karyawan.nama}'? (y/n): ")
    val konfirmasi = readLine()

    if (konfirmasi.equals("y", ignoreCase = true)) {
        daftarKaryawan.remove(karyawan)
        println("  Karyawan '${karyawan.nama}' berhasil dihapus!")
    } else {
        println("  Penghapusan dibatalkan.")
    }
}

// ── MENU 5 : SHOW DATA (KEY - VALUE) ──
fun showKeyValue() {
    garis()
    println("  SHOW DATA (KEY : VALUE)")
    garis()

    if (daftarKaryawan.isEmpty()) {
        println("  (Belum ada data karyawan)")
        return
    }

    print("  Masukkan ID karyawan: ")
    val inputId = readLine()?.toIntOrNull()

    if (inputId == null) {
        println("  Input tidak valid!")
        return
    }

    val karyawan = daftarKaryawan.find { it.id == inputId }
    if (karyawan == null) {
        println("  Karyawan dengan ID $inputId tidak ditemukan!")
        return
    }

    val data = DataKaryawan(karyawan)

    garisTipis()
    // Tampilkan dalam format KEY : VALUE menggunakan Map
    val keyValue: Map<String, String> = mapOf(
        "id"       to karyawan.id.toString(),
        "nama"     to data.nama,
        "jabatan"  to karyawan.jabatan,
        "gaji"     to data.gaji,
        "noHp"     to data.noHp    
    )

    keyValue.forEach { (key, value) ->
        println("  %-10s : %s".format(key, value))
    }
    garisTipis()
}

// ── MAIN ──
fun main() {
    println("\n  Selamat datang di Aplikasi Manajemen Karyawan")

    var running = true
    while (running) {
        garis()
        println("  MENU UTAMA")
        garis()
        println("  1. Tambah Data Karyawan")
        println("  2. List Data Karyawan")
        println("  3. Edit Data Karyawan")
        println("  4. Hapus Data Karyawan")
        println("  5. Show Data (Key - Value)")
        println("  0. Keluar")
        garis()
        print("  Pilih menu: ")

        when (readLine()?.trim()) {
            "1"  -> tambahData()
            "2"  -> listData()
            "3"  -> editData()
            "4"  -> hapusData()
            "5"  -> showKeyValue()
            "0"  -> {
                println("\n  Sampai jumpa! Kotlin CLI by Aan")
                running = false
            }
            else -> println("  Menu tidak valid, coba lagi.")
        }

        if (running) {
            print("\n  Tekan Enter untuk kembali ke menu...")
            readLine()
        }
    }
}
