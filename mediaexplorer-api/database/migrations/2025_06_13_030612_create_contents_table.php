<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('contents', function (Blueprint $table) {
            $table->id();
            $table->string('title');
            $table->string('tipo');
            $table->text('description')->nullable();
            $table->string('imagen')->nullable();
            $table->unsignedBigInteger('categoriaId');
            $table->timestamps();
            
            $table->foreign('categoriaId')->references('id')->on('categories')->onDelete('cascade');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('contents');
    }
};