<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('contents', function (Blueprint $table) {
            $table->string('id')->primary(); // Cambiado a string para coincidir con la app
            $table->string('title');
            $table->string('tipo');
            $table->text('description');
            $table->string('imagen');
            $table->string('categoria_id');
            $table->timestamps();

            $table->foreign('categoria_id')
                  ->references('id')
                  ->on('categories')
                  ->onDelete('cascade');
        });
    }

    public function down()
    {
        Schema::dropIfExists('contents');
    }
};