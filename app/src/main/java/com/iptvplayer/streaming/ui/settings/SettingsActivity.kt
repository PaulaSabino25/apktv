package com.iptvplayer.streaming.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.iptvplayer.streaming.R
import com.iptvplayer.streaming.databinding.ActivitySettingsBinding
import com.iptvplayer.streaming.repository.IPTVRepository
import com.iptvplayer.streaming.security.SecurityManager
import com.iptvplayer.streaming.ui.servers.ServersFragment
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var securityManager: SecurityManager
    private lateinit var repository: IPTVRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        securityManager = SecurityManager(this)
        repository = IPTVRepository(this)
        
        setupToolbar()
        setupPreferences()
        loadCurrentSettings()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Configurações"
    }
    
    private fun setupPreferences() {
        binding.apply {
            // Server Management
            cardServers.setOnClickListener {
                // Navigate to servers fragment
                val fragment = ServersFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            
            // Security Settings
            switchPinProtection.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    showPinSetupDialog()
                } else {
                    securityManager.removePIN()
                }
            }
            
            switchStealthMode.setOnCheckedChangeListener { _, isChecked ->
                securityManager.enableStealthMode(isChecked)
            }
            
            switchFakeCrash.setOnCheckedChangeListener { _, isChecked ->
                securityManager.enableFakeCrash(isChecked)
            }
            
            switchAdultFilter.setOnCheckedChangeListener { _, isChecked ->
                securityManager.enableAdultFilter(isChecked)
            }
            
            // Data Management
            cardClearHistory.setOnClickListener {
                showClearHistoryDialog()
            }
            
            cardClearFavorites.setOnClickListener {
                showClearFavoritesDialog()
            }
            
            cardCleanupData.setOnClickListener {
                cleanupOldData()
            }
            
            // Emergency
            cardEmergencyWipe.setOnClickListener {
                showEmergencyWipeDialog()
            }
            
            cardSecurityCheck.setOnClickListener {
                performSecurityCheck()
            }
        }
    }
    
    private fun loadCurrentSettings() {
        binding.apply {
            switchPinProtection.isChecked = securityManager.hasPINSet()
            switchStealthMode.isChecked = securityManager.isStealthModeEnabled()
            switchFakeCrash.isChecked = securityManager.isFakeCrashEnabled()
            switchAdultFilter.isChecked = securityManager.isAdultFilterEnabled()
        }
    }
    
    private fun showPinSetupDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Configurar PIN")
        builder.setMessage("Digite um PIN de 4 dígitos para proteger o aplicativo:")
        
        val inputView = layoutInflater.inflate(R.layout.dialog_pin_input, null)
        builder.setView(inputView)
        
        builder.setPositiveButton("Definir") { dialog, _ ->
            // Get PIN from input
            val pin = "1234" // Replace with actual input
            securityManager.setPIN(pin)
            Toast.makeText(this, "PIN definido com sucesso", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            binding.switchPinProtection.isChecked = false
            dialog.dismiss()
        }
        
        builder.show()
    }
    
    private fun showClearHistoryDialog() {
        AlertDialog.Builder(this)
            .setTitle("Limpar Histórico")
            .setMessage("Deseja limpar todo o histórico de reprodução?")
            .setPositiveButton("Limpar") { _, _ ->
                clearHistory()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun showClearFavoritesDialog() {
        AlertDialog.Builder(this)
            .setTitle("Limpar Favoritos")
            .setMessage("Deseja remover todos os canais favoritos?")
            .setPositiveButton("Limpar") { _, _ ->
                clearFavorites()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun showEmergencyWipeDialog() {
        AlertDialog.Builder(this)
            .setTitle("⚠️ LIMPEZA DE EMERGÊNCIA")
            .setMessage("ATENÇÃO: Isso irá apagar TODOS os dados do aplicativo permanentemente. Esta ação não pode ser desfeita!")
            .setPositiveButton("CONFIRMAR") { _, _ ->
                showEmergencyConfirmation()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun showEmergencyConfirmation() {
        val panicCode = securityManager.generatePanicCode()
        
        AlertDialog.Builder(this)
            .setTitle("Confirmação Final")
            .setMessage("Digite o código de pânico para confirmar:\n\n$panicCode")
            .setPositiveButton("Executar") { _, _ ->
                emergencyWipe()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun clearHistory() {
        lifecycleScope.launch {
            try {
                repository.clearHistory()
                Toast.makeText(this@SettingsActivity, "Histórico limpo", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun clearFavorites() {
        lifecycleScope.launch {
            try {
                val favorites = repository.getFavoriteChannels()
                favorites.forEach { favorite ->
                    repository.removeFromFavorites(favorite.id)
                }
                Toast.makeText(this@SettingsActivity, "Favoritos removidos", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun cleanupOldData() {
        lifecycleScope.launch {
            try {
                repository.cleanupOldData()
                Toast.makeText(this@SettingsActivity, "Dados antigos limpos", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun emergencyWipe() {
        lifecycleScope.launch {
            try {
                securityManager.triggerEmergencyWipe()
                Toast.makeText(this@SettingsActivity, "Limpeza de emergência executada", Toast.LENGTH_SHORT).show()
                
                // Restart app
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finishAffinity()
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun performSecurityCheck() {
        val result = securityManager.isDeviceSecure()
        
        val message = if (result.isSecure) {
            "✅ Dispositivo seguro"
        } else {
            "⚠️ Avisos de segurança:\n\n${result.warnings.joinToString("\n• ", "• ")}"
        }
        
        AlertDialog.Builder(this)
            .setTitle("Verificação de Segurança")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
